package com.example.demo.service.impl

import com.example.demo.domain.TablePoint
import com.example.demo.service.PlayerService
import java.util.*
import kotlin.collections.HashSet


class PlayerServiceImpl(private val playerTablePoints: HashSet<TablePoint>,
                        private val pointsForShouting: HashSet<TablePoint?>
) :PlayerService{
    override fun hit(): TablePoint {
        val list = ArrayList<TablePoint>(this.pointsForShouting)
        val random = Random()
        return list[random.nextInt(pointsForShouting.size)]
    }
}
