package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.utils.Position

/** Class for the Police Car
 */
class PoliceCar(
    override val id: Int,
    override val base: Base,
    override val staffCapacity: Int,
    override val vehicleHeight: Int,
    override var position: Position? = null,
    val criminalCapacity: Int,
    var transportedCriminals: Int = 0,
) : Vehicle(
    id,
    VehicleType.POLICE_CAR,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {
    val criminalsStillFitting get() = criminalCapacity - transportedCriminals
}
