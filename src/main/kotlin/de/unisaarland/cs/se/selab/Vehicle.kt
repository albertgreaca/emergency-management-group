package de.unisaarland.cs.se.selab

/** Class for all Vehicles
 */
open class Vehicle(
    open val id: Int,
    open val vehicleType: VehicleType,
    open val base: Base,
    open val staffCapacity: Int,
    open val vehicleHeight: Int,
    open var position: Position? = null

) {
    var available: Boolean = true
    var baseWaitingTicks: Int = 0
    var targetEmergency: Emergency? = null

    /**
     * setter for the baseWaitingTicks
     */
    fun setBaseWaitingTicks(ticks: Int) {
        baseWaitingTicks = ticks
    }

    /**
     * setter for the Position
     */
    fun setPosition(pos: Position) {
        position = pos
    }

    /**
     * setter for the Availability
     */
    fun setAvailable(value: Boolean) {
        available = value
    }

    /**
     * moves the vehicle, changes the position
     */
    fun move() {
        position!!.advance()
    }

    /**
     * checks if the vehicle is reroutable
     * @return true if reroutable, else false
     */
    fun reroutable(): Boolean {
        if (position == null) {
            return false
        }
        if (position!!.arrivalTicks == 0) {
            return false
        }
        return true
    }

    /**
     * checks if the vehicle is reallocatable
     * @return true if reallocatable, else false
     */
    fun reallocatable(em: Emergency): Boolean {
        if (position == null) {
            return false
        }
        if (position!!.arrivalTicks == 0) {
            return false
        }
        if (targetEmergency == null || em.severity < targetEmergency!!.severity) {
            return false
        }

        if (baseWaitingTicks != 0) {
            return false
        }
        return true
    }

    /**
     * reroute the vehicle, change position
     * @return true if changed the position, else false
     */
    fun reroute(): Boolean {
        if (position!!.isDrivingBack) {
            val pos = Dijkstra.dijkstraBackToBase(
                position!!.roadList.get(0),
                position!!.distanceFromStart,
                position!!.distanceFromEnd,
                position!!.destinationVertex!!,
                base.getId(),
                vehicleHeight
            )
            if (!position!!.equals(pos)) {
                position = pos
                return true
            }
            return false
        }
        if (!position!!.isDrivingBack) {
            val pos = Dijkstra.dijkstraReroute(
                position!!.roadList.get(0),
                position!!.distanceFromStart,
                position!!.distanceFromEnd,
                position!!.destinationVertex!!,
                targetEmergency!!.road,
                vehicleHeight
            )
            if (!position!!.equals(pos)) {
                position = pos
                return true
            }
            return false
        }
        return false
    }

    fun sendBackToBase() {
        //  Position pos = Dijkstra.dijkstraHeigth()
        //  position = pos
    }
}
