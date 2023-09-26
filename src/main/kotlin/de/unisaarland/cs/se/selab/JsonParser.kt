package de.unisaarland.cs.se.selab

import PoliceDepartment
import org.json.JSONObject
import java.io.File

class JsonParser(private val gm: GraphMap, private val file1: File, private val file2: File) {
    val id = "id" // because duplications for keywords
    fun parseVehicles(): Boolean {
        val jsonObject = JSONObject(file1.readText())
        val vehiclesObject = jsonObject.getJSONArray("Vehicles")
        for (i in 0 until vehiclesObject.length()) {
            val currVehicle = vehiclesObject.getJSONObject(i)
            val id = currVehicle.getInt(id)
            if (id < 0) return false
            val baseId = currVehicle.getInt("baseID")
            val vehicleType: VehicleType = currVehicle.get("VehicleType") as VehicleType
            val vehicleHeight = currVehicle.getInt("vehicleHeight")
            val staffCapacity = currVehicle.getInt("staffCapacity")
            when (vehicleType) {
                VehicleType.POLICE_CAR -> {
                    val crimCapacity = currVehicle.getInt("criminalCapacity")
                    // PoliceCar(id, baseId, staffCapacity, vehicleHeight, null, crimCapacity, 0)
                } // add to BAse and findBase is needed
                VehicleType.FIRE_TRUCK_WATER -> {
                    val waterCapacity = currVehicle.getInt("waterCapacity")
                    // FireTruckWater(id, baseId, staffCapacity, vehicleHeight, null, waterCapacity, waterCapacity)
                }
                // create FireTruckWater
                VehicleType.FIRE_TRUCK_LADDER -> {
                    val ladderLength = currVehicle.getInt("ladderLength") == ladderReference
                    //   FireTruckLadder(id, baseId, staffCapacity, vehicleHeight, null, ladderLength)
                } // create FireTruck
                VehicleType.AMBULANCE -> {
                    // Ambulance(id, baseId, staffCapacity, vehicleHeight, null, false)
                } else -> {
                    // Vehicle(id, vehicleType, baseId, staffCapacity, vehicleHeight, null)
                }
            }
            // create Vehicle
        }
        return true
    }

    fun parseBases(): Boolean {
        val fireDepartment = FireDepartment()
        val policeDepartment = PoliceDepartment()
        val hospital = Hospital()
        val jsonObject = JSONObject(file1.readText())
        val basesObject = jsonObject.getJSONArray("Bases")
        for (i in 0 until basesObject.length()) {
            val currBase = basesObject.getJSONObject(i)
            val id = currBase.getInt(id)
            if (id < 0) return false
            val baseType = currBase.getString("baseType")
            val locationId = currBase.getInt("location")
            if (locationId < 0) return false
            val location = gm.getVertex(locationId) ?: return false
            val staffs = currBase.getInt("staffs")
            if (staffs < 1) return false
            when (baseType) {
                "FIRE_STATION" -> {
                    Base(id, staffs, location, mutableListOf())
                    // add Department
                }

                "POLICE_STATION" -> {
                    val dogs = currBase.getInt("dogs") // create Police Station
                }

                "HOSPITAL" -> {
                    val doctor = currBase.getInt("doctors") // create Hospital}
                }
            } // add Department
        }
        return true
    }

    fun parseEmergency(): Boolean {
        var res = true
        val jsonObject = JSONObject(file2.readText())
        val emerArray = jsonObject.getJSONArray("EmergencyCalls")
        for (i in 0 until emerArray.length()) {
            val currEmer = emerArray.getJSONObject(i)
            val id = currEmer.getInt(id)
            if (id < 0) res = false
            val tick = currEmer.getInt("tick")
            if (tick < 1) res = false
            val village = currEmer.getString("village")
            val roadName = currEmer.getString("roadName")
            val road = gm.getRoad(village, roadName) ?: return false
            val type: EmergencyType = currEmer.get("emergencyType") as EmergencyType
            val severity = currEmer.getInt("severity")
            if (severity < 1 || severity > 3) res = false
            val handleTime = currEmer.getInt("handleTime")
            if (handleTime < 1) res = false
            val maxDuration = currEmer.getInt("maxDuration")
            if (maxDuration < 2) res = false
            val resources = Resource(mutableListOf(), 0, 0, 0, 0) // resourceFactory

            // Simulation.addEmergency(Emergency(id, tick, road, type, severity, handleTime, maxDuration, resources))
        }
        res = res && true
        return res
    }

    fun parseEvents(): Boolean {
        var res = true
        val jsonObject = JSONObject(file2.readText())
        val eventArray = jsonObject.getJSONArray("events")
        for (i in 0 until eventArray.length()) {
            val currEvent = eventArray.getJSONObject(i)
            val id = currEvent.getInt(id)
            val type = currEvent.getString("type")
            val tick = currEvent.getInt("tick")
            val duration = currEvent.getInt("duration")
            // if (duration < 1 || id < 0 || tick < 0) res = false
            when (type) {
                "VEHICLE_UNAVAILABLE" -> res = res && parseVehicleUnavailableEvent(currEvent, id, tick, duration)
                "ROAD_CLOSURE" -> res = res && parseRoadClosureEvent(currEvent, id, tick, duration)
                "CONSTRUCTION_SITE" -> res = res && parseConstructionSite(currEvent, id, tick, duration)
                "TRAFFIC_JAM" -> res = res && parseTrafficJAM(currEvent, id, tick, duration)
                "RUSH_HOUR" -> res = res && parseRushHour(currEvent, id, tick, duration)
            }
        }
        res = res && true
        return res
    }

    private fun parseVehicleUnavailableEvent(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val vehicleId = currEvent.getInt("vehicleID")
        if (vehicleId < 0 || duration < 1 || id < 0) res = false
        if (tick < 0) res = false
        // need list of bases
        res = res && true
        return res
    }

    private fun parseRoadClosureEvent(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val sourceVertex = currEvent.getInt(source)
        val targetVertex = currEvent.getInt(target)
        if (sourceVertex < 0 || targetVertex < 0 || duration < 1) res = false
        if (id < 0 || tick < 0) res = false
        // find road via a new find function with source and target
        res = res && true
        return res
    }

    private fun parseConstructionSite(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val sourceVertex = currEvent.getInt(source)
        val targetVertex = currEvent.getInt(target)
        // find road via a new find function with source and target
        val oneWayStreet = currEvent.getBoolean("oneWayStreet")
        val factor = currEvent.getInt("factor")
        if (factor < 1 || sourceVertex < 0 || targetVertex < 0) res = false
        if (duration < 1 || id < 0 || tick < 0) res = false
        // create
        res = res && true
        return res
    }

    private fun parseTrafficJAM(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val factor = currEvent.getInt("factor")
        val sourceVertex = currEvent.getInt(source)
        val targetVertex = currEvent.getInt(target)
        if (sourceVertex < 0 || targetVertex < 0 || factor < 1) res = false
        if (duration < 1 || id < 0 || tick < 0) res = false
        // find Road
        // create
        res = res && true
        return res
    }

    private fun parseRushHour(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val roadTypes: MutableList<VehicleType> = currEvent.get("roadTypes") as MutableList<VehicleType>
        if (duration < 1 || id < 0 || tick < 0) return false
        // getRoads from GraphMap with types
        res = res && true
        return res
    }

    companion object {
        const val ladderReference = 40 // because magic number
        const val source = "source" // because duplicates
        const val target = "target" // because duplicates
    }
}
