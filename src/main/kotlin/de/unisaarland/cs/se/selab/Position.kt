package de.unisaarland.cs.se.selab

import java.lang.Integer.min

class Position(
    var roadList: MutableList<Road>,
    var roadIndex: Int,
    var positionOnRoad: Int,
    var destinationVertex: Vertex?,
    var arrivalTicks: Int,
    var distance: Int,
    var startedThisTick: Boolean
) {

    fun advance() {
        //TODO
    }

    fun isEqual(o: Position): Boolean {
        return this.roadList == o.roadList && this.roadIndex == o.roadIndex &&
            this.positionOnRoad == o.positionOnRoad &&
            this.destinationVertex == o.destinationVertex &&
            this.arrivalTicks == o.arrivalTicks
    }
    fun smaller(o: Position): Boolean {
        // TODO
        /*var n: Int = roadList.size
        var m: Int = o.roadList.size
        for (i in 0..min(n,m) - 1)
            */
    }
}
