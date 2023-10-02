package de.unisaarland.cs.se.selab.bases

import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.resources.Request
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.utils.Position
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckLadder
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import kotlin.math.min

/**
 * Bases of the departments
 */
open class Base(
    val id: Int,
    var staff: Int,
    val location: Vertex,
    val vehicles: MutableList<Vehicle>,
) {

    companion object {
        const val maxCriminalCapacity = 4
        const val maxWaterCapacity = 2400
        const val ladder40 = 40
        const val ladder30 = 30
    }

    private val nextBases: MutableList<Base> = mutableListOf()

    /**
     * add Vehicle to Base Vehicle List
     */
    fun addVehicle(v: Vehicle) {
        vehicles.add(v)
    }

    /**
     * checks if the needed Resource for the Emergency are
     * available
     * @return the stuff that is still missing
     */
    fun requestResources(em: Emergency) {
        val r = em.resources
        val neededVehicles = r.vehicles

        // create a list of all vehicles in the base which could potentially be allocated
        val potentialVehicles = vehicles.filter {
            it.available &&
                it.vehicleType in neededVehicles
        }.toMutableList()

        val vehiclesToAllocate: MutableList<Vehicle> = mutableListOf()
        for (i in min(neededVehicles.size, potentialVehicles.size) downTo 0) {
            vehiclesToAllocate.clear()
            val res = trySendThisNumberOfAssets(i, em, potentialVehicles)
            if (res != null) {
                vehiclesToAllocate.addAll(requireNotNull(res))
                break
            }
        }

        val loggerlist = mutableListOf<Vehicle>()

        // allocate all vehicles in the list
        for (vehicle in vehiclesToAllocate) {
            allocateVehicle(vehicle, em, loggerlist)
        }
        loggerlist.sortBy { it.id }
        for (ve in loggerlist) {
            Logger.logAssetAllocation(ve.id, em.id, requireNotNull(ve.position?.arrivalTicks))
        }
        // var availableBaseVehicles = this.vehicles.filter { it.available }.toMutableList()
        // val vehicTypesToRequest = mutableListOf<VehicleType>()
        // TODO : implement
        /*for (vt in neededVehicles) {
            // Todo : something happening here
            var p = 0
            p += 0
        }*/
        // Todo : Implement actual allocation of vehicles in vehiclesToAllocate
        /*for (vehicle in vehiclesToAllocate) {
            var p = 0
            p += 0
        }*/
        // return Resource(mutableListOf(), 0, 0, 0, 0)
    }

    /**
     * allocates Vehicles
     */
    fun allocateVehicle(vehicle: Vehicle, em: Emergency, loggerlist: MutableList<Vehicle>) {
        // calculate and set the position of the vehicle
        vehicle.position = Dijkstra.dijkstraHeight(this.location.realid, em.road, vehicle.vehicleHeight)
        // set position to started this tick
        requireNotNull(vehicle.position).startedThisTick = true
        // reduce the available staff of the vehicle
        this.staff -= vehicle.staffCapacity
        // set the target emergency of the vehicle
        vehicle.targetEmergency = em
        // remove the vehicle type from the list of needed vehicle types
        em.resources.vehicles.remove(vehicle.vehicleType)
        // special vehicles editen
        when (vehicle) {
            is PoliceCar -> {
                if (em.resources.criminalAmount >= vehicle.criminalCapacity) {
                    em.resources.criminalAmount -= vehicle.criminalCapacity
                    vehicle.transportedCriminals = vehicle.criminalCapacity
                } else {
                    vehicle.transportedCriminals = em.resources.criminalAmount
                    em.resources.criminalAmount = 0
                }
            }

            is FireTruckWater -> {
                if (em.resources.waterAmount >= vehicle.waterCapacity) {
                    em.resources.waterAmount -= vehicle.waterCapacity
                    vehicle.waterTransported = vehicle.waterCapacity
                } else {
                    vehicle.waterTransported = em.resources.waterAmount
                    em.resources.waterAmount = 0
                }
            }

            is Ambulance -> {
                if (em.resources.patientAmount >= 1) {
                    em.resources.patientAmount -= 1
                    vehicle.patientOnBoard = true
                } else {
                    vehicle.patientOnBoard = false
                }
            }
        }
        if (em.resources.countInstancesOf(VehicleType.FIRE_TRUCK_LADDER) == 0) {
            em.resources.ladderLength = 0
        }
        // set vehicle availability false
        vehicle.available = false
        // add vehicle to list in emergency
        em.addVehicle(vehicle)
        loggerlist.add(vehicle)
    }

    /**
     * @returns the ordered list of vehicles to be allocated if allocation of n vehicles is possible, null otherwise
     */
    fun trySendThisNumberOfAssets(k: Int, em: Emergency, vehicles: MutableList<Vehicle>): MutableList<Vehicle>? {
        vehicles.sortBy { it.id }

        // try each combination of n vehicles starting with the lowest id's
        val n = vehicles.size
        val tries = IntArray(k) { it }
        val cur: MutableList<Vehicle> = mutableListOf()
        while (true) {
            cur.clear()
            for (i in 0..k - 1) {
                cur.add(vehicles[tries[i]])
            }
            if (checkCombination(em, cur)) {
                return cur
            }
            var pos = -1
            for (i in k - 1 downTo 0) {
                if (tries[i] != n - (k - i)) {
                    pos = i
                    break
                }
            }
            if (pos == -1) {
                break
            }
            tries[pos]++
            for (i in pos + 1..k - 1) {
                tries[i] = tries[i - 1] + 1
            }
        }
        return null
    }

    /**
     * @returns true if the combination of vehicles can fulfill every constraint of the resource, false otherwise
     */
    fun checkCombination(em: Emergency, vehicles: MutableList<Vehicle>): Boolean {
        var validCombination = true
        val resource = em.resources
        var staffNeeded = 0
        var fittingCriminals = 0
        var fittingWater = 0
        var numberOfPoliceCars = 0
        var numberOfWaterTrucks = 0
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
        }
        if (staffNeeded > this.staff) validCombination = false
        if (resource.criminalAmount - fittingCriminals >
            maxCriminalCapacity * (
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
        for (vehicleType in VehicleType.values()) {
            if (resource.countInstancesOf(vehicleType) < vehicles.count { it.vehicleType == vehicleType }) {
                return false
            }
        }

        // check if all vehicles arrive in time using dijkstra
        for (vec in vehicles) {
            val pos = Dijkstra.dijkstraHeight(this.location.realid, em.road, vec.vehicleHeight)
            if (requireNotNull(pos).arrivalTicks + Simulation.currentTick + em.handleTime > em.tick + em.maxDuration) {
                return false
            }
        }
        return validCombination
    }

    /**
     * Initiates reallocation phase, looks through own vehicles if can be reallocated to emergency
     * @return a Resource with what's still missing
     */
    fun reallocateResources(em: Emergency): Resource {
        // list of all successfully reallocated vehicles for logging
        val reallocatedList = mutableListOf<Vehicle>()

        // list of all reallocatable vehicles
        val reallocatableVehics = this.vehicles.filter { it.reallocatable(em) }

        // copy of the needed vehicle types so that we have two separate lists for iterating and removing
        val copyOfNeededTypes = em.resources.vehicles.toMutableList()

        // for each vehic type in resource
        // val listread = mutableListOf<Pair<Emergency, VehicleType>>()
        for (vt in copyOfNeededTypes) {
            for (vehic in reallocatableVehics) {
                var successful = false
                // if vehicle has wrong type skip to next vehicle
                if (vehic.vehicleType != vt) continue

                // calculate new position of vehicle
                val height = vehic.vehicleHeight
                val curpos = requireNotNull(vehic.position)
                var pos: Position?
                if (curpos.roadList.isEmpty() || curpos.destinationVertex == null) {
                    val road1: Road = em.road
                    curpos.destinationVertex = this.location
                    pos = Dijkstra.dijkstraReroute(
                        road1,
                        0,
                        0,
                        requireNotNull(curpos.destinationVertex),
                        em.road,
                        height
                    )
                } else {
                    pos = Dijkstra.dijkstraReroute(
                        curpos.roadList[0],
                        curpos.distanceFromStart,
                        curpos.distanceFromEnd,
                        requireNotNull(curpos.destinationVertex),
                        em.road,
                        height
                    )
                }

                // if vehicle cannot arrive in time then skip to next vehicle
                if (requireNotNull(pos).arrivalTicks + Simulation.currentTick + em.handleTime > em.tick + em.maxDuration) {
                    continue
                }
                successful = true

                // set the vehicle's position to the calculated position
                vehic.position = pos

                // remove the vehicle from the assigned vehicles of previous emergency
                requireNotNull(vehic.targetEmergency).assignedVehicles.remove(vehic)

                // add the vehicle to the assigned vehicles of new emergency
                em.assignedVehicles.add(vehic)

                // if previous emergency was in handledEmergencies, move it back to startingEmergencies
                if (requireNotNull(vehic.targetEmergency) in EMCC.handledEmergencies) {
                    EMCC.handledEmergencies.remove(vehic.targetEmergency)
                    EMCC.startingEmergencies.add(requireNotNull(vehic.targetEmergency))
                }

                // set the vehicle's target emergency to the new emergency
                vehic.targetEmergency = em

                // remove the vehicle type from the list of needed vehicle types
                em.resources.vehicles.remove(vt)

                // update water/criminals/patients for special vehicle types
                reallocateResources(em, vehic)

                // add vehicle to logging list
                reallocatedList.add(vehic)

                if (successful) break
            }
        }

        // log all reallocations ordered by asset id
        reallocatedList.sortBy { it.id }
        for (v in reallocatedList) {
            Logger.logAssetReallocation(v.id, em.id)
        }
        return em.resources
    }

    private fun reallocateResources(em: Emergency, vehic: Vehicle) {
        when (vehic) {
            is FireTruckWater -> em.resources.waterAmount -= vehic.waterCapacity
            is Ambulance -> em.resources.patientAmount--
            is PoliceCar -> em.resources.criminalAmount -= vehic.criminalCapacity
            is FireTruckLadder -> ladderlength(em, vehic)
            else -> {}
        }
        if (em.resources.waterAmount < 0) {
            em.resources.waterAmount = 0
        }
        if (em.resources.patientAmount < 0) {
            em.resources.patientAmount = 0
        }
        if (em.resources.criminalAmount < 0) {
            em.resources.criminalAmount = 0
        }
    }

    private fun ladderlength(em: Emergency, vehic: Vehicle) {
        if (vehic is FireTruckLadder) {
            if (requireNotNull(em.resources.ladderLength) >= ladder40 && vehic.getLadderLength40()) {
                em.resources.ladderLength = 0
            }
            if (requireNotNull(em.resources.ladderLength) >= ladder30) {
                em.resources.ladderLength = 0
            }
        }
    }

    /**
     * helper function to check available vehicles for corresponding type
     */
    private fun findVehicleofType(v: VehicleType, vehicleList: List<Vehicle>): Vehicle? {
        for (vehicle in vehicleList) {
            if (vehicle.vehicleType == v) {
                return vehicle
            }
        }
        return null
    }

    /**
     * Each tick due to events recalculates the new distance of the other bases
     * updates list of next bases
     */
    fun calculateNextBases() {
        // TODO : implement
        val nextcalculatedBases = Dijkstra.dijkstraRequest(this.location.realid)
        this.nextBases.clear()
        this.nextBases.addAll(nextcalculatedBases)
        return
        // recalculates the distances of the other bases to this base
        // updates 'nextBases' with new distances and sorts the list
        // this calculation has to be made once per tick since new events might change the distances of the next bases
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextPoliceBase(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base is PoliceStation && ok) {
                return base
            }
        }
        return null
    }

    /**
     * returns next Base of Type Hospital
     */
    fun getNextHospital(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base is Hospital && ok) {
                return base
            }
        }
        return null
    }

    /**
     * returns next Base of Type Police Station
     */
    fun getNextFireBase(b: Base): Base? {
        var ok = false
        for (base in nextBases) {
            if (base == b) {
                ok = true
            } else if (base !is Hospital && base !is PoliceStation && ok) {
                return base
            }
        }
        return null
    }

    /**
     * send a request to the next closest Base
     * adds request to Requestlist in EMCC
     */
    fun makeRequest(em: Emergency, nextBase: Base) {
        // create a request
        val req = Request(EMCC.nextRequestId, em, nextBase)
        // add it to the list of requests
        EMCC.requests.add(req)
        // log that a new request was made
        Logger.logAssetRequest(req.getId(), nextBase.id, em.id)
        // count up the id of the next request
        EMCC.nextRequestId++
    }
}
