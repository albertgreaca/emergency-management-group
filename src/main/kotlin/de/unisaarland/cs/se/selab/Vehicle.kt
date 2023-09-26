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
     * getter for the id
     * @return id of the vehicle
     */
    fun getId(): Int {
        return id
    }

    /**
     * getter for the base
     * @return base of the vehicle
     */
    fun getBase(): Base {
        return base
    }

    /**
     * getter for the VehicleType
     * @return VehicleType of the vehicle
     */
    fun getVehicleType(): VehicleType {
        return vehicleType
    }

    /**
     * getter for the staffCapacity
     * @return staffCapacity of the vehicle
     */
    fun getStaffCapacity(): Int {
        return staffCapacity
    }

    /**
     * getter for the VehicleHeight
     * @return Height of the vehicle
     */
    fun getVehicleHeight(): Int {
        return vehicleHeight
    }

    /**
     * getter for the baseWaitingTicks
     * @return baseWaitingTicks of the vehicle
     */
    fun getBaseWaitingTicks(): Int {
        return baseWaitingTicks
    }

    /**
     * setter for the baseWaitingTicks
     */
    fun setBaseWaitingTicks(ticks: Int) {
        baseWaitingTicks = ticks
    }

    /**
     * getter for the Position
     * @return Position of the vehicle
     */
    fun getPosition(): Position? {
        return position
    }

    /**
     * setter for the Position
     */
    fun setPosition(pos: Position) {
        position = pos
    }

    /**
     * getter for the Availability
     * @return Availability of the vehicle
     */
    fun isAvailable(): Boolean {
        return available
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
        if (getPosition()!!.arrivalTicks == 0) {
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
        if (getPosition()!!.arrivalTicks == 0) {
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
        // Position pos = Dijkstra.dijkstraHeigth()
        // if (!position.equals(pos)){
        // position = pos
        return true
        // }
        // return false }
    }
}
