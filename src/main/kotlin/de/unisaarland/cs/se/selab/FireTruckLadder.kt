package de.unisaarland.cs.se.selab

class FireTruckLadder(
    private val id: Int,
    private val base: Base,
    private val staffCapacity: Int,
    private val vehicleHeight: Int,
    private var position: Position? = null,
    private val ladderLength40: Boolean,
) : Vehicle(
    id,
    VehicleType.FIRE_TRUCK_LADDER,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {
    fun getLadderLength40(): Boolean {
        return ladderLength40
    }

}