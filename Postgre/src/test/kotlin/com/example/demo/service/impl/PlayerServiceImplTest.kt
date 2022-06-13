package com.example.demo.service.impl

import com.example.demo.domain.TablePoint
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.text.MatchesPattern.matchesPattern
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class PlayerServiceImplTest {

    lateinit var playerServiceImpl: PlayerServiceImpl

    lateinit var pingPongTableService: PingPongTableServiceImpl


    @BeforeEach
    fun setUp() {
        pingPongTableService = PingPongTableServiceImpl()
        playerServiceImpl = PlayerServiceImpl(
            pingPongTableService.getPlayerOneTablePoints(),
            pingPongTableService.getPlayerOneTablePointsForShouting()
        )
    }

    @Test
    fun hit() {
        var tablePoint: TablePoint = playerServiceImpl.hit()
        println(tablePoint.toString())
        assertThat(tablePoint.toString(), matchesPattern("TabelPoint \\{x=\\d{1,2}, y=\\d{1,2}\\}"))
    }
}