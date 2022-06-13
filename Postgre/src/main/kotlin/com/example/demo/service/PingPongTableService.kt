package com.example.demo.service

import com.example.demo.domain.TablePoint


interface PingPongTableService {
    fun getAllTablePoints(): HashSet<TablePoint>
    fun getPlayerOneTablePoints(): HashSet<TablePoint>?
    fun getPlayerTwoTablePoints(): HashSet<TablePoint>?
    fun getPlayerOneTablePointsForShouting(): HashSet<TablePoint?>?
    fun getPlayerTwoTablePointsForShouting(): HashSet<TablePoint?>?
}