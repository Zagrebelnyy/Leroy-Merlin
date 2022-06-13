package com.example.demo.web.controllers

import com.example.demo.domain.Game
import com.example.demo.domain.Player
import com.example.demo.kafka.Producer
import com.example.demo.repository.model.dto.NewGameDTO
import com.example.demo.service.GameService
import com.example.demo.service.impl.PingPongTableServiceImpl
import com.example.demo.service.impl.PlayerServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.web.bind.annotation.*
import ru.leroymerlin.billing.GameResult
import ru.leroymerlin.billing.NewGameMessage
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.*

@RestController
@RequestMapping("kafka")
class KafkaController {
    @Autowired
    lateinit var producer: Producer

    @Autowired
    lateinit var gameService: GameService

    lateinit var pingPongTableService: PingPongTableServiceImpl

    @Autowired
    lateinit var kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry

    @PostMapping("/play")
    @ResponseBody
    fun create(@RequestBody newGame: NewGameDTO): Game {

        val newGameMessage = NewGameMessage()

        val game: Game

        if (newGame.nameOne.isNotEmpty() && newGame.nameTwo.isNotEmpty() &&
            newGame.ageOne != -1 && newGame.ageTwo != -1 && newGame.points != -1
        ) {

            val points = BigDecimal(newGame.points)
            newGameMessage.setPoint(points)
            newGameMessage.setFirstPlayer(ru.leroymerlin.billing.Player(newGame.nameOne, newGame.ageOne))
            newGameMessage.setSecondPlayer(ru.leroymerlin.billing.Player(newGame.nameTwo, newGame.ageTwo))

            producer.send("billing-trainee-new-game-input-v1", newGameMessage)


            val playerOne = Player(newGame.nameOne)
            val playerTwo = Player(newGame.nameTwo)

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

            pingPongTableService = PingPongTableServiceImpl()

            val playerServiceOne = PlayerServiceImpl(
                pingPongTableService.getPlayerOneTablePoints(),
                pingPongTableService.getPlayerOneTablePointsForShouting()
            )
            val playerServiceTwo = PlayerServiceImpl(
                pingPongTableService.getPlayerTwoTablePoints(),
                pingPongTableService.getPlayerTwoTablePointsForShouting()
            )

            val gameResult = GameResult()

            val score = gameService.run(pingPongTableService, newGame.points, playerServiceOne, playerServiceTwo)

            val result = score.split(":")

            if (result[0].toInt() > result[1].toInt()) {
                gameResult.firstPlayer = newGame.nameOne
                gameResult.score = BigDecimal(result[0])
            } else {
                gameResult.firstPlayer = newGame.nameTwo
                gameResult.score = BigDecimal(result[1])
            }
            gameResult.gameTime = Clock.systemDefaultZone().instant()
            println(gameResult)

            producer.send("billing-trainee-game-result-v1", gameResult)

            game = Game(newGame.points, playerOne, playerTwo, time, score)


        } else {
            producer.send("billing-trainee-new-game-input-v1-dlt", newGame.toString())
            kafkaListenerEndpointRegistry.stop()
            game = Game(0, Player(""), Player(""), "", "")
        }
        return game
    }
}