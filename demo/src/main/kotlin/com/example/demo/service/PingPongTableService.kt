package com.example.demo.service

import com.example.demo.domain.TablePoint
import org.springframework.stereotype.Service


interface PingPongTableService {
    fun getAllTablePoints(): HashSet<TablePoint>
    fun getPlayerOneTablePoints(): HashSet<TablePoint>?
    fun getPlayerTwoTablePoints(): HashSet<TablePoint>?
    fun getPlayerOneTablePointsForShouting(): HashSet<TablePoint?>?
    fun getPlayerTwoTablePointsForShouting(): HashSet<TablePoint?>?
}