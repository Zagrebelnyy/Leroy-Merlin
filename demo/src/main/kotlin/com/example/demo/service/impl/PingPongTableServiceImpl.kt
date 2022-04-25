package com.example.demo.service.impl

import com.example.demo.domain.TablePoint
import com.example.demo.service.PingPongTableService
import org.springframework.stereotype.Component


@Component
class PingPongTableServiceImpl : PingPongTableService {
    private val tablePoints: HashSet<TablePoint>
    private val playerOneTablePoints: HashSet<TablePoint>
    private val playerTwoTablePoints: HashSet<TablePoint>
    private val playerOneTablePointsForShouting: HashSet<TablePoint?>
    private val playerTwoTablePointsForShouting: HashSet<TablePoint?>

    companion object {
        private const val TABLE_SIZE_IN_POINTS = 10
        private val LEFT_TOP_POINT = TablePoint(1, 1)
    }

    private fun buildTablePoints(startPoint: TablePoint, pointsByX: Int, pointsByY: Int): HashSet<TablePoint> {
        val tablePoints = HashSet<TablePoint>()
        for (i in startPoint.x until startPoint.x + pointsByX) {
            for (j in startPoint.y until startPoint.y + pointsByY) {
                tablePoints.add(TablePoint(i, j))
            }
        }
        println("Created tablePoints: $tablePoints")
        return tablePoints
    }


    override fun getAllTablePoints(): HashSet<TablePoint> {
        return this.tablePoints
    }

    override fun getPlayerOneTablePoints(): HashSet<TablePoint> {
        return this.playerOneTablePoints
    }

    override fun getPlayerTwoTablePoints(): HashSet<TablePoint> {
        return this.playerTwoTablePoints
    }

    override fun getPlayerOneTablePointsForShouting(): HashSet<TablePoint?> {
        return this.playerOneTablePointsForShouting
    }

    override fun getPlayerTwoTablePointsForShouting(): HashSet<TablePoint?> {
        return this.playerTwoTablePointsForShouting
    }

    private fun getPointForShouting(allPoints: Set<TablePoint>, excludedPoints: Set<TablePoint>): HashSet<TablePoint?> {
        val pointForShouting: HashSet<TablePoint?> = HashSet<TablePoint?>(allPoints)
        pointForShouting.removeAll(excludedPoints)
        println("Player points for shouting $pointForShouting")
        return pointForShouting

    }

    init {
        println("Generate table")
        this.tablePoints = buildTablePoints(LEFT_TOP_POINT, TABLE_SIZE_IN_POINTS, TABLE_SIZE_IN_POINTS)
        this.playerOneTablePoints = buildTablePoints(TablePoint(3, 2), 6, 4)
        this.playerTwoTablePoints = buildTablePoints(TablePoint(3, 6), 6, 4)
        playerOneTablePointsForShouting = getPointForShouting(tablePoints, playerOneTablePoints)
        playerTwoTablePointsForShouting = getPointForShouting(tablePoints, playerTwoTablePoints)
        println("Table generated successfully")
    }


}