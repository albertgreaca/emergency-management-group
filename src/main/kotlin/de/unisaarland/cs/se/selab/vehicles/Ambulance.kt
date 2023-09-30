package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.utils.Position

/** Class for the Ambulance
 */
data class Ambulance(
    override val id: Int,
    override val base: Base,
    override val staffCapacity: Int,
    override val vehicleHeight: Int,
    override var position: Position? = null,
    var patientOnBoard: Boolean,
) : Vehicle(
    id,
    VehicleType.AMBULANCE,
    base,
    staffCapacity,
    vehicleHeight,
    position
)
