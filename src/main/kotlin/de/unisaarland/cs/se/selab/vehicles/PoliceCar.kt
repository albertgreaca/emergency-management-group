package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
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

    /**
     * checks if the vehicle is reallocatable
     * @return true if reallocatable, else false
     */
    override fun reallocatable(em: Emergency): Boolean {
        if (position == null) {
            return false
        }
        if (requireNotNull(position).arrivalTicks == 0) {
            return false
        }
        if (baseWaitingTicks != 0 || criminalsStillFitting == 0) {
            return false
        }
        if (targetEmergency != null && em.severity <= requireNotNull(targetEmergency).severity) {
            return false
        }
        return true
    }
}
