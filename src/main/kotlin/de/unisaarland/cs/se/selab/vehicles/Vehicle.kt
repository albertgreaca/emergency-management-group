package de.unisaarland.cs.se.selab.vehicles

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.utils.Position

/** Class for all Vehicles
 */
open class Vehicle(
    open val id: Int,
    open val vehicleType: VehicleType,
    open val base: Base,
    open val staffCapacity: Int,
    open val vehicleHeight: Int,
    open var position: Position? = null

) {
    var available: Boolean = true
    var baseWaitingTicks: Int = 0
    var targetEmergency: Emergency? = null

    /**
     * moves the vehicle, changes the position
     */
    fun move() {
        requireNotNull(position).advance()
    }

    /**
     * checks if the vehicle is reroutable
     * @return true if reroutable, else false
     */
    fun reroutable(): Boolean {
        if (position == null) {
            return false
        }
        if (requireNotNull(position).arrivalTicks == 0) {
            return false
        }
        return true
    }

    /**
     * checks if the vehicle is reallocatable
     * @return true if reallocatable, else false
     */
    fun reallocatable(em: Emergency): Boolean {
        if (position == null) {
            return false
        }
        if (requireNotNull(position).arrivalTicks == 0) {
            return false
        }
        if (targetEmergency == null || em.severity < requireNotNull(targetEmergency).severity) {
            return false
        }

        if (baseWaitingTicks != 0) {
            return false
        }
        return true
    }

    /**
     * reroute the vehicle, change position
     * @return true if changed the position, else false
     */
    fun reroute(): Boolean {
        if (requireNotNull(position).isDrivingBack) {
            val pos = Dijkstra.dijkstraRerouteBackToBase(
                requireNotNull(position).roadList[0],
                requireNotNull(position).distanceFromStart,
                requireNotNull(position).distanceFromEnd,
                requireNotNull(requireNotNull(position).destinationVertex),
                base.location.realid,
                vehicleHeight
            )
            if (!requireNotNull(position).isEqual(requireNotNull(pos))) {
                position = pos
                return true
            }
            return false
        }
        if (!requireNotNull(position).isDrivingBack) {
            val pos = Dijkstra.dijkstraReroute(
                requireNotNull(position).roadList[0],
                requireNotNull(position).distanceFromStart,
                requireNotNull(position).distanceFromEnd,
                requireNotNull(requireNotNull(position).destinationVertex),
                requireNotNull(targetEmergency).road,
                vehicleHeight
            )
            if (!requireNotNull(position).isEqual(requireNotNull(pos))) {
                position = pos
                return true
            }
            return false
        }
        return false
    }

    /**
     * to send vehicle back to base
     */
    fun sendBackToBase() {
        val pos = Dijkstra.dijkstraBackToBase(
            requireNotNull(requireNotNull(position).vertexList[requireNotNull(position).vertexList.size - 1]).realid,
            base.location.realid,
            vehicleHeight
        )
        pos?.isDrivingBack = true

        position = pos
    }
}
