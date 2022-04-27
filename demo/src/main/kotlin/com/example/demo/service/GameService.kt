package com.example.demo.service

import com.example.demo.domain.Player
import com.example.demo.service.impl.PingPongTableServiceImpl
import org.springframework.stereotype.Service


interface GameService {
    fun run(pingPongTable: PingPongTableServiceImpl, points: Int, playerOne: PlayerService, playerTwo: PlayerService): String
}