class Position(private var roadList: List<Road>, private var roadIndex: Int, private var positionOnRoad: Int,
               private var destinyVertex: Vertex, private var arrivalTicks: Int, private var startedThisTick: Boolean) {

    fun getRoadList(): List<Road> {
        return roadList
    }

    fun getRoadIndex(): Int {
        return roadIndex
    }

    fun getPositionOnRoad(): Int {
        return positionOnRoad
    }

    fun getDestinyVertex(): Vertex {
        return destinyVertex
    }

    fun getArrivalTicks(): Int {
        return arrivalTicks
    }

    fun getStartedThisTick(): Int {
        return startedThisTick
    }

    fun advance() {
        //TODO
    }

    override fun equals(o: Any?): Boolean {
        if (o is Position) {
            return this.getRoadList() == o.getRoadList()
                    && this.roadIndex == o.getRoadIndex()
                    && this.getPositionOnRoad() == o.getPositionOnRoad()
                    && this.getDestinyVertex() == o.getDestinyVertex()
                    && this.getArrivalTicks() == o.getArrivalTicks()
        }
        return false
    }
}