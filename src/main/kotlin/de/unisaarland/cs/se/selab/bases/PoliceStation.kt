package de.unisaarland.cs.se.selab.bases

import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.resources.Request
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType

/**
 * base for the police department
 */
class PoliceStation(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>, var dogs: Int) : Base(
    id,
    staff,
    location,
    vehicles
) {
    /**
     * @returns true if the combination of vehicles can fulfill every constraint of the resource, false otherwise
     */
    override fun checkCombination(em: Emergency, vehicles: MutableList<Vehicle>): Boolean {
        var validCombination = true
        val resource = em.currentResources
        var staffNeeded = 0
        var fittingCriminals = 0
        var fittingWater = 0
        var numberOfPoliceCars = 0
        var numberOfWaterTrucks = 0
        var numberOfK9Cars = 0
        for (vec in vehicles) {
            staffNeeded += vec.staffCapacity
            if (vec is PoliceCar) {
                fittingCriminals += vec.criminalCapacity
                numberOfPoliceCars++
            }
            if (vec is FireTruckWater) {
                fittingWater += vec.waterCapacity
                numberOfWaterTrucks++
            }
            if (vec.vehicleType == VehicleType.K9_POLICE_CAR) {
                numberOfK9Cars++
            }
        }
        if (staffNeeded > this.staff || numberOfK9Cars > this.dogs) validCombination = false
        if (resource.criminalAmount - fittingCriminals >
            MaxCriminalCapacity * (
                resource.countInstancesOf(VehicleType.POLICE_CAR) -
                    numberOfPoliceCars
                )
        ) {
            return false
        }
        if (resource.waterAmount - fittingWater >
            maxWaterCapacity * (
                resource.countInstancesOf(VehicleType.FIRE_TRUCK_WATER) -
                    numberOfWaterTrucks
                )
        ) {
            return false
        }

        // for each vehicle-type, check if there are more vehicles in the combination that required
        if (!checkIfTooManyVehicles(resource, vehicles)) {
            validCombination = false
        }

        // check if all vehicles arrive in time using dijkstra
        if (!checkAllVehiclesArriveInTime(vehicles, em)) {
            validCombination = false
        }
        return validCombination
    }

    private fun checkIfTooManyVehicles(resource: Resource, vehicles: MutableList<Vehicle>): Boolean {
        for (vehicleType in VehicleType.values()) {
            if (resource.countInstancesOf(vehicleType) < vehicles.count { it.vehicleType == vehicleType }) {
                return false
            }
        }
        return true
    }

    private fun checkAllVehiclesArriveInTime(vehicles: MutableList<Vehicle>, em: Emergency): Boolean {
        for (vec in vehicles) {
            val pos = Dijkstra.dijkstraHeight(this.location.realid, em.road, vec.vehicleHeight)
            if (requireNotNull(pos).arrivalTicks + Simulation.currentTick + em.handleTime > em.tick + em.maxDuration) {
                return false
            }
        }
        return true
    }
}
