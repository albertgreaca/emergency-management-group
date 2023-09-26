package de.unisaarland.cs.se.selab

import PoliceDepartment
import org.json.JSONObject
import java.io.File
/**
 * Class to parse JSON Files
*/
class JsonParser(private val gm: GraphMap, private val file1: File, private val file2: File) {

    /**
     * function to parse the Vehicles
     */
    fun parseVehicles(): Boolean {
        var res = true
        val jsonObject = JSONObject(file1.readText())
        val vehiclesObject = jsonObject.getJSONArray("Vehicles")
        for (i in 0 until vehiclesObject.length()) {
            val currVehicle = vehiclesObject.getJSONObject(i)
            val id = currVehicle.getInt(Companion.ID)
            val baseId = currVehicle.getInt("baseID")
            val vehicleType: VehicleType = currVehicle.get("VehicleType") as VehicleType
            val vehicleHeight = currVehicle.getInt("vehicleHeight")
            val staff = currVehicle.getInt("staffCapacity")
            when (vehicleType) {
                VehicleType.POLICE_CAR -> res = res && parsePoliceCar(currVehicle, id, baseId, staff)
                VehicleType.FIRE_TRUCK_WATER -> res = res && parseFireTruckWater(currVehicle, id, baseId, staff)
                VehicleType.FIRE_TRUCK_LADDER -> res = res && parseFireTruckLadder(currVehicle, id, baseId, staff)
                VehicleType.AMBULANCE -> res = res && parseAmbulance(currVehicle, id, baseId, staff)
                else -> {
                    // Vehicle(id, vehicleType, baseId, staffCapacity, vehicleHeight, null)
                }
            }
        }
        // res = res && validateVehicles(newVehicle)
        Logger.logInitInfo(file1.name, res)
        return res
    }

    private fun parseAmbulance(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int): Boolean {
        var res = true
        if (id < 0 || baseId < 0 || staffs < 0) res = false
        // Ambulance(id, baseId, staffCapacity, vehicleHeight, null, false)
        return res
    }

    private fun parseFireTruckLadder(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int): Boolean {
        var res = true
        if (id < 0 || baseId < 0 || staffs < 0) res = false
        val ladderLength = currVehicle.getInt("ladderLength")
        if (ladderLength < LADDER_REFERENCE || ladderLength > LADDER_REFERENCE_BIG) res = false
        val ladder40 = ladderLength == LADDER_REFERENCE
        //   FireTruckLadder(id, baseId, staffCapacity, vehicleHeight, null, ladder40)
        return res
    }

    private fun parseFireTruckWater(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int): Boolean {
        var res = true
        if (id < 0 || baseId < 0 || staffs < 0) res = false
        val waterCapacity = currVehicle.getInt("waterCapacity")
        if (waterCapacity != WATER_LITTLE && waterCapacity != WATER_MIDDLE && waterCapacity != WATER_BIG) res = false
        // FireTruckWater(id, baseId, staffCapacity, vehicleHeight, null, waterCapacity, waterCapacity)
        return res
    }
    private fun parsePoliceCar(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int): Boolean {
        var res = true
        val crimCapacity = currVehicle.getInt("criminalCapacity")
        if (id < 0 || baseId < 0 || staffs < 0) res = false
        if (crimCapacity < 1 || crimCapacity > MAX_CRIM_CAPACITY) res = false
        // PoliceCar(id, baseId, staffCapacity, vehicleHeight, null, crimCapacity, 0)
        // add to BAse and findBase is needed
        return res
    }

    /**
     * function to parse Bases
     */
    fun parseBases(): Boolean {
        val fireDepartment = FireDepartment()
        val policeDepartment = PoliceDepartment()
        val hospital = Hospital()
        val jsonObject = JSONObject(file1.readText())
        val basesObject = jsonObject.getJSONArray("Bases")
        for (i in 0 until basesObject.length()) {
            val currBase = basesObject.getJSONObject(i)
            val id = currBase.getInt(Companion.ID)
            if (id < 0) return false
            val baseType = currBase.getString("baseType")
            val locationId = currBase.getInt("location")
            if (locationId < 0) return false
            val location = gm.getVertex(locationId) ?: return false
            val staffs = currBase.getInt("staffs")
            if (staffs < 1) return false
            when (baseType) {
                "FIRE_STATION" -> {
                    val newBase = Base(id, staffs, location, mutableListOf())
                    location.setBase(newBase)
                    // add Department
                }

                "POLICE_STATION" -> {
                    val dogs = currBase.getInt("dogs") // create Police Station
                    val newBase = PoliceStation()
                    // location.setBase(newBase)
                }

                "HOSPITAL" -> {
                    val doctor = currBase.getInt("doctors") // create Hospital}
                }
            } // add Department
        }
        return true
    }

    /**
     * function to parse Emergencies
     */
    fun parseEmergencies(): Boolean {
        var res = true
        val jsonObject = JSONObject(file2.readText())
        val emerArray = jsonObject.getJSONArray("EmergencyCalls")
        for (i in 0 until emerArray.length()) {
            val currEmer = emerArray.getJSONObject(i)
            val id = currEmer.getInt(Companion.ID)
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
            // resources tofo
            val resources = Resource(mutableListOf(), 0, 0, 0, 0) // resourceFactory
            val newEmergency = Emergency(id, tick, road, type, severity, handleTime, maxDuration, resources)
            Simulation.addEmergency(newEmergency)
        }
        res = res && true
        return res
    }

    /**
     * funtion to parse Events
     */
    fun parseEvents(): Boolean {
        var res = true
        val jsonObject = JSONObject(file2.readText())
        val eventArray = jsonObject.getJSONArray("events")
        for (i in 0 until eventArray.length()) {
            val currEvent = eventArray.getJSONObject(i)
            val id = currEvent.getInt(Companion.ID)
            val type = currEvent.getString("type")
            val tick = currEvent.getInt("tick")
            val duration = currEvent.getInt("duration")
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
        val sourceVertex = currEvent.getInt(SOURCE)
        val targetVertex = currEvent.getInt(TARGET)
        if (sourceVertex < 0 || targetVertex < 0 || duration < 1) res = false
        if (id < 0 || tick < 0) res = false
        // find road via a new find function with source and target
        res = res && true
        return res
    }

    private fun parseConstructionSite(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        val sourceVertex = currEvent.getInt(SOURCE)
        val targetVertex = currEvent.getInt(TARGET)
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
        val sourceVertex = currEvent.getInt(SOURCE)
        val targetVertex = currEvent.getInt(TARGET)
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
        if (duration < 1 || id < 0 || tick < 0) res = false
        // getRoads from GraphMap with types
        res = res && true
        return res
    }

    companion object {
        const val LADDER_REFERENCE = 40 // because magic number
        const val LADDER_REFERENCE_BIG = 70
        const val SOURCE = "source" // because duplicates
        const val TARGET = "target" // because duplicates
        const val ID = "id" // because duplications for keywords
        const val WATER_LITTLE = 600
        const val WATER_MIDDLE = 1200
        const val WATER_BIG = 2400
        const val MAX_CRIM_CAPACITY = 4
    }
}
