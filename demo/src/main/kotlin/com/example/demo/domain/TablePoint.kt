package com.example.demo.domain

import java.util.*


data class TablePoint(val x: Int, val y: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TablePoint) return false
        val (x1, y1) = other as TablePoint
        return x == x1 && y == y1
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    override fun toString(): String {
        return "TabelPoint {x=$x, y=$y}"
    }
}