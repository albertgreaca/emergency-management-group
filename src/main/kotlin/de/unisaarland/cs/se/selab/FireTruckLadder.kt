package de.unisaarland.cs.se.selab

/** Class for the Fire Truck Ladder
 */
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

    /**
     * getter for the ladder length
     * @return true if ladder length is 40, else false
     */
    fun getLadderLength40(): Boolean {
        return ladderLength40
    }
}
