package de.unisaarland.cs.se.selab.utils

import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import java.lang.Integer.min

/**
 * this class models the position of a vehicle
 */
class Position(
    var roadList: MutableList<Road>,
    var vertexList: MutableList<Vertex>,
    var distanceFromStart: Int,
    var distanceFromEnd: Int,
    var destinationVertex: Vertex?,
    var distance: Int,
    var arrivalTicks: Int,
    var startedThisTick: Boolean = false,
    var isDrivingBack: Boolean = false
) {

    companion object {
        const val WEIGHT_TO_GO = 10
    }

    /**
     * advances the position by one tick
     */
    fun advance() {
        if (startedThisTick) {
            startedThisTick = false
            return
        }
        var weightLeft = WEIGHT_TO_GO
        while (weightLeft > 0 && roadList.isNotEmpty()) {
            distanceFromEnd--
            distanceFromStart++
            weightLeft--
            if (distanceFromEnd == 0) {
                roadList.removeAt(0)
                distanceFromStart = 0
                if (!roadList.isEmpty()) {
                    distanceFromEnd = roadList[0].getActualWeight()
                }
            }
        }
        arrivalTicks--
    }

    /**
     * checks if two positions are equal
     */
    fun isEqual(o: Position): Boolean {
        return this.roadList == o.roadList && this.vertexList == o.vertexList &&
            this.distanceFromStart == o.distanceFromStart && this.distanceFromEnd == o.distanceFromEnd &&
            this.destinationVertex == o.destinationVertex && this.distance == o.distance
    }

    /**
     * checks if current position is lexicographically smaller than the other position given as argument
     */
    fun smaller(o: Position): Boolean {
        // TODO
        val n: Int = vertexList.size
        val m: Int = o.vertexList.size
        for (i in 0..min(n, m) - 1) {
            if (vertexList[i].id < o.vertexList[i].id) {
                return true
            } else {
                if (vertexList[i].id > o.vertexList[i].id) {
                    return false
                }
            }
        }
        return n < m
    }
}
