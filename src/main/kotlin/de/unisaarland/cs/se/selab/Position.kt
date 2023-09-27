package de.unisaarland.cs.se.selab

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

    /**
     * advances the position by one tick
     */
    fun advance() {
        if (startedThisTick) {
            // TODO
        } else {
            startedThisTick = true
        }
    }

    /**
     * checks if two positions are equal
     */
    fun isEqual(o: Position): Boolean {
        return this.roadList == o.roadList &&
            this.distanceFromStart == o.distanceFromStart && this.distanceFromEnd == o.distanceFromEnd &&
            this.destinationVertex == o.destinationVertex && this.distance == o.distance
    }

    /**
     * checks if current position is lexicographically smaller than the other position given as argument
     */
    fun smaller(o: Position): Boolean {
        // TODO
        var n: Int = vertexList.size
        var m: Int = o.vertexList.size
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
