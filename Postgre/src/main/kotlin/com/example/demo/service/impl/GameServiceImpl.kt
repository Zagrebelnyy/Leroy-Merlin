package com.example.demo.service.impl

import com.example.demo.domain.TablePoint
import com.example.demo.service.GameService
import com.example.demo.service.PlayerService
import org.springframework.stereotype.Service


@Service
class GameServiceImpl : GameService {

    override fun run(
        pingPongTable: PingPongTableServiceImpl, points: Int,
        playerOne: PlayerService, playerTwo: PlayerService
    ): String {
        var score1 = 0 //Счёт первого игрока
        var score2 = 0 //Счёт второго игрока

        var currentPlayer = playerOne //Текущий игрок (всегда начинает первый)
        var currentTablePoint: TablePoint? //Текущий удар

        while (true) {
            currentTablePoint = currentPlayer.hit()
            println(currentTablePoint.toString())
            if (currentPlayer.equals(playerOne)) {
                println("Производится удар игроком под номером один")
                if (pingPongTable.getPlayerTwoTablePointsForShouting()!!.contains(currentTablePoint)) {
                    score1++
                    println("Игроку под номером один присваивается очко")
                } else {
                    score2++
                    println("Игрок под номером один промахивается. Ход переходит к игроку под номером два")
                    currentPlayer = playerTwo
                }
            } else {
                println("Производится удар игроком под номером два")
                if (pingPongTable.getPlayerOneTablePointsForShouting()!!.contains(currentTablePoint)) {
                    score2++
                    println("Игроку под номером два присваивается очко")
                } else {
                    score1++
                    println("Игрок под номером два промахивается. Ход переходит к игроку под номером один")
                    currentPlayer = playerOne
                }
            }
            if (score1 == points || score2 == points) {
                if (score1 == points)
                    println("Игрок под номером один победил")
                else
                    println("Игрок под номером два победил")

                println("Текущий счёт $score1 : $score2")

                return "$score1:$score2"
            }
        }
    }
}