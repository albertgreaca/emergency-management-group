package de.unisaarland.cs.se.selab.bases

import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.resources.Resource
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

    override fun allocateVehicle(vehicle: Vehicle, em: Emergency, loggerlist: MutableList<Vehicle>) {
        // calculate and set the position of the vehicle
        vehicle.position = Dijkstra.dijkstraHeight(this.location.realid, em.road, vehicle.vehicleHeight)
        // set position to started this tick
        requireNotNull(vehicle.position).startedThisTick = true
        // reduce the available staff of the vehicle
        this.staff -= vehicle.staffCapacity
        // reduce K9 dogs if vehicle is K9 police car
        if (vehicle.vehicleType == VehicleType.K9_POLICE_CAR) this.dogs--
        // set the target emergency of the vehicle
        vehicle.targetEmergency = em
        // remove the vehicle type from the list of needed vehicle types
        em.currentResources.vehicles.remove(vehicle.vehicleType)
        // reduce needed patients/water/criminals for special vehicles
        allocateWhen(em, vehicle)
        // set vehicle availability false
        vehicle.available = false
        // add vehicle to list in emergency
        em.addVehicle(vehicle)
        loggerlist.add(vehicle)
    }

    /**
     * @returns true if the combination of vehicles can fulfill every constraint of the resource, false otherwise
     */
    override fun checkCombinationWithoutArrivalTime(em: Emergency, vehicles: MutableList<Vehicle>): Boolean {
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
}
