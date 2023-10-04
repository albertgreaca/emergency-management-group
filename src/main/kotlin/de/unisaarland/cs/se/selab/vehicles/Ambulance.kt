package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.mainlogic.EMCC
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
) {
    override fun reallocatable(em: Emergency): Boolean {
        if (position == null) {
            return false
        }
        if (requireNotNull(position).arrivalTicks == 0) {
            return false
        }
        if (baseWaitingTicks != 0 || patientOnBoard) {
            return false
        }
        if (targetEmergency != null && !EMCC.resolvedOrFailedEmergencies.contains(targetEmergency) &&
            em.severity <= requireNotNull(targetEmergency).severity
        ) {
            return false
        }
        return true
    }
}
