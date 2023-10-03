package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.utils.Position

/** Class for the Fire Truck Water
 */
class FireTruckWater(
    override val id: Int,
    override val base: Base,
    override val staffCapacity: Int,
    override val vehicleHeight: Int,
    override var position: Position? = null,
    val waterCapacity: Int,
) : Vehicle(
    id,
    VehicleType.FIRE_TRUCK_WATER,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {
    var waterTransported: Int = waterCapacity
}
