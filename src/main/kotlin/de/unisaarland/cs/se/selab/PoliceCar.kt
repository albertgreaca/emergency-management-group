package de.unisaarland.cs.se.selab

/** Class for the Police Car
 */
class PoliceCar(
    override val id: Int,
    override val base: Base,
    override val staffCapacity: Int,
    override val vehicleHeight: Int,
    override var position: Position? = null,
    val criminalCapacity: Int,
    var transportedCriminals: Int
) : Vehicle(
    id,
    VehicleType.POLICE_CAR,
    base,
    staffCapacity,
    vehicleHeight,
    position
)
