package de.unisaarland.cs.se.selab

/** Class for the position of a vehicle
 */
class Position(
    private var roadList: MutableList<Road>,
    private var roadIndex: Int,
    private var positionOnRoad: Int,
    private var destinyVertex: Vertex,
    private var arrivalTicks: Int,
    private var startedThisTick: Boolean
) {
    /**
     * @return list of roads the vehicle has to drive on
     * the path, way
     */
    fun getRoadList(): MutableList<Road> {
        return roadList
    }

    /**
     * @return index in the road list
     * road the vehicle is driving on
     */
    fun getRoadIndex(): Int {
        return roadIndex
    }

    /**
     * @return actual weight on the road where the vehicle is
     */
    fun getPositionOnRoad(): Int {
        return positionOnRoad
    }

    /**
     * @return vertex of the destination
     */
    fun getDestinyVertex(): Vertex {
        return destinyVertex
    }

    /**
     * @return needed ticks to arrive
     */
    fun getArrivalTicks(): Int {
        return arrivalTicks
    }

    /**
     * @return true if vehicle just started driving in this tick, else false
     */
    fun getStartedThisTick(): Boolean {
        return startedThisTick
    }

    /**
     * setter for the arrival ticks
     */
    fun setArrivalTicks(ticks: Int) {
        arrivalTicks = ticks
    }

    /**
     * changing the position after vehicle was updated
     */
    fun advance() {
        //TODO
    }

    /**
     * equals method for positions
     */
    override fun equals(o: Any?): Boolean {
        if (o is Position) {
            return this.getRoadList() == o.getRoadList() &&
                    this.roadIndex == o.getRoadIndex() &&
                    this.getPositionOnRoad() == o.getPositionOnRoad() &&
                    this.getDestinyVertex() == o.getDestinyVertex() &&
                    this.getArrivalTicks() == o.getArrivalTicks()
        }
        return false
    }
}
