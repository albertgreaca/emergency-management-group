package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.utils.Position

/** Class for the Fire Truck Ladder
 */
class FireTruckLadder(
    override val id: Int,
    override val base: Base,
    override val staffCapacity: Int,
    override val vehicleHeight: Int,
    override var position: Position? = null,
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
