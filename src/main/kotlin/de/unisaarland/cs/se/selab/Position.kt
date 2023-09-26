package de.unisaarland.cs.se.selab

import java.lang.Integer.min

class Position(
    var roadList: MutableList<Road>,
    var vertexList: MutableList<Vertex>,
    var positionOnRoad: Int,
    var destinationVertex: Vertex?,
    var distance: Int,
    var arrivalTicks: Int,
    var startedThisTick: Boolean = false,
    var roadIndex: Int = 0
) {

    fun advance() {
        if(startedThisTick) {
            // TODO
        } else {
            startedThisTick = true
        }
    }

    fun isEqual(o: Position): Boolean {
        return this.roadList == o.roadList && this.vertexList == o.vertexList && this.roadIndex == o.roadIndex &&
            this.positionOnRoad == o.positionOnRoad &&
            this.destinationVertex == o.destinationVertex &&
            this.arrivalTicks == o.arrivalTicks
    }
    fun smaller(o: Position): Boolean {
        // TODO
        var n: Int = vertexList.size
        var m: Int = o.vertexList.size
        for (i in 0..min(n, m) - 1)
            if (vertexList[i].id < o.vertexList[i].id) {
                return true
            } else {
                if (vertexList[i].id > o.vertexList[i].id) {
                    return false
                }
            }
        return n < m
    }
}
