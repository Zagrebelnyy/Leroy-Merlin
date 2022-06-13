package com.example.demo.service.impl

import org.hamcrest.MatcherAssert
import org.hamcrest.text.MatchesPattern
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class GameServiceImplTest {

    lateinit var gameServiceImpl: GameServiceImpl

    @Autowired
    lateinit var pingPongTableService: PingPongTableServiceImpl

    @BeforeEach
    fun setUp() {
        gameServiceImpl = GameServiceImpl()
        pingPongTableService = PingPongTableServiceImpl()
    }

    @Test
    fun run() {
        val points = 11

        val result = gameServiceImpl.run(
            pingPongTableService, points, PlayerServiceImpl(
                pingPongTableService.getPlayerOneTablePoints(),
                pingPongTableService.getPlayerOneTablePointsForShouting()
            ), PlayerServiceImpl(
                pingPongTableService.getPlayerTwoTablePoints(),
                pingPongTableService.getPlayerTwoTablePointsForShouting()
            )
        )

        MatcherAssert.assertThat(
            result,
            MatchesPattern.matchesPattern("\\d{1,2}\\:\\d{1,2}")
        )

    }
}