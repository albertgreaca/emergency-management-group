package de.unisaarland.cs.se.selab

open class Vehicle(
    private val id: Int,
    private val vehicleType: VehicleType,
    private val base: Base,
    private val staffCapacity: Int,
    private val vehicleHeight: Int,
    private var position: Position? = null

) {
    private var available: Boolean = true
    private var baseWaitingTicks: Int = 0
    private var targetEmergency: Emergency? = null
    fun getId(): Int {
        return id
    }

    fun getBase(): Base {
        return base
    }

    fun getVehicleType(): VehicleType {
        return vehicleType
    }

    fun getStaffCapacity(): Int {
        return staffCapacity
    }

    fun getVehicleHeight(): Int {
        return vehicleHeight
    }

    fun getBaseWaitingTicks(): Int {
        return baseWaitingTicks
    }
    fun setBaseWaitingTicks(ticks: Int) {
        baseWaitingTicks = ticks
    }

    fun getPosition(): Position? {
        return position
    }
    fun setPosition(pos: Position) {
        position = pos
    }

    fun isAvailable(): Boolean {
        return available
    }

    fun setAvailable(value: Boolean) {
        available = value
    }

    fun move() {
        position!!.advance()
    }

    fun reroutable(): Boolean {

        if (position == null) {
            return false
        }
        if (getPosition()!!.getArrivalTicks() == 0) {
            return false
        }
        return true
    }

    fun reallocatable(em: Emergency): Boolean {
        if (position == null) {
            return false
        }
        if (getPosition()!!.getArrivalTicks() == 0) {
            return false
        }
        if (targetEmergency == null || em.getSeverity() < targetEmergency!!.getSeverity()) {
            return false
        }

        if(baseWaitingTicks != 0 ){
            return false
        }
        return true
    }

    fun reroute(): Boolean {
        Position pos = Dijkstra.dijkstraHeigth()
        if (!position.equals(pos)){
            position = pos
            return true
        }
        return false
    }


}
