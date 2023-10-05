package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.bases.Hospital
import de.unisaarland.cs.se.selab.bases.PoliceStation
import de.unisaarland.cs.se.selab.bases.departments.AmbulanceDepartment
import de.unisaarland.cs.se.selab.bases.departments.FireDepartment
import de.unisaarland.cs.se.selab.bases.departments.PoliceDepartment
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.events.ConstructionSiteEvent
import de.unisaarland.cs.se.selab.events.RoadClosureEvent
import de.unisaarland.cs.se.selab.events.RushHourEvent
import de.unisaarland.cs.se.selab.events.TrafficJamEvent
import de.unisaarland.cs.se.selab.events.VehicleUnavailableEvent
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.resources.ResourceFactory
import de.unisaarland.cs.se.selab.utils.getSchema
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.FireTruckLadder
import de.unisaarland.cs.se.selab.vehicles.FireTruckWater
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.everit.json.schema.ValidationException
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Class to parse JSON Files
 */
class JsonParser(private val gm: GraphMap, private val file1: File, private val file2: File) {
    private val jsonStringConfig1 = file1.readText()
    private val jsonStringConfig2 = file2.readText()
    private val listBases = mutableMapOf<Base, Boolean>()
    private var hos = false
    private var pol = false
    private var fir = false
    private val vehicleidlist = mutableMapOf<Int, Vehicle>()

    /**
     * function to parse the Vehicles
     */
    fun parseVehicles(): Boolean {
        var res = true
        val jsonObject = JSONObject(jsonStringConfig1)
        val vehiclesObject = jsonObject.getJSONArray("vehicles")
        for (i in 0 until vehiclesObject.length()) {
            val currVehicle = vehiclesObject.getJSONObject(i)
            val id = currVehicle.getInt(ID)
            res = res && !vehicleidlist.contains(id)
            val baseId = currVehicle.getInt("baseID")
            val vehicleType: VehicleType = parseVehicleType(currVehicle.get("vehicleType")) ?: return false
            val vehicleHeight = currVehicle.getInt("vehicleHeight")
            val staff = currVehicle.getInt("staffCapacity")
            res = res && whenstatement(vehicleType, currVehicle, id, baseId, staff, vehicleHeight)
            if (!res) {
                return false
            }
        }
        return res && !listBases.containsValue(false)
    }

    private fun whenstatement(
        vehicleType: VehicleType,
        currVehicle: JSONObject,
        id: Int,
        baseId: Int,
        staff: Int,
        vehicleHeight: Int
    ): Boolean {
        var res = true
        val base: Base?
        when (vehicleType) {
            VehicleType.POLICE_CAR -> {
                base = EMCC.policeDepartment?.findBase(baseId)
                if (base == null || currVehicle.has(LADDERLENGTH) || currVehicle.has(WATERCAPACITY)) {
                    res = false
                }
                res = res && parsePoliceCar(currVehicle, id, baseId, staff, vehicleHeight)
            }

            VehicleType.FIRE_TRUCK_WATER -> {
                base = EMCC.fireDepartment?.findBase(baseId)
                if (base == null || currVehicle.has(LADDERLENGTH) || currVehicle.has(CRIMINALCAPACITY)) {
                    res = false
                }
                res = res && parseFireTruckWater(currVehicle, id, baseId, staff, vehicleHeight)
            }
            else -> {
                res = res && whenstatement2(vehicleType, currVehicle, id, baseId, staff, vehicleHeight)
            }
        }
        return res
    }

    private fun whenstatement2(
        vehicleType: VehicleType,
        currVehicle: JSONObject,
        id: Int,
        baseId: Int,
        staff: Int,
        vehicleHeight: Int
    ): Boolean {
        var res = true
        val base: Base?
        when (vehicleType) {
            VehicleType.FIRE_TRUCK_LADDER -> {
                base = EMCC.fireDepartment?.findBase(baseId)
                if (base == null || currVehicle.has(WATERCAPACITY) || currVehicle.has(CRIMINALCAPACITY)) {
                    res = false
                }
                res = res && parseFireTruckLadder(currVehicle, id, baseId, staff, vehicleHeight)
            }

            VehicleType.AMBULANCE -> {
                base = EMCC.ambulanceDepartment?.findBase(baseId)
                if (base == null || currVehicle.has(LADDERLENGTH)) {
                    res = false
                }
                if (currVehicle.has(WATERCAPACITY) || currVehicle.has(CRIMINALCAPACITY)) {
                    res = false
                }
                res = res && parseAmbulance(id, baseId, staff, vehicleHeight)
            }

            else -> {
                res = res && parseRestVehicle(currVehicle, id, baseId, staff, vehicleHeight, vehicleType)
            }
        }
        return res
    }

    private fun parseRestVehicle(
        currVehicle: JSONObject,
        id: Int,
        baseId: Int,
        staffs: Int,
        height: Int,
        type: VehicleType
    ): Boolean {
        var res = true
        val base: Base?
        val utils = JsonParserUtils()
        res = utils.oki(type, currVehicle)
        when (type) {
            VehicleType.EMERGENCY_DOCTOR_CAR -> {
                base = EMCC.ambulanceDepartment?.findBase(baseId)
            }
            VehicleType.FIREFIGHTER_TRANSPORTER -> {
                base = EMCC.fireDepartment?.findBase(baseId)
            }
            VehicleType.FIRE_TRUCK_TECHNICAL -> {
                base = EMCC.fireDepartment?.findBase(baseId)
            }
            VehicleType.POLICE_MOTORCYCLE -> {
                base = EMCC.policeDepartment?.findBase(baseId)
            }
            else -> {
                base = EMCC.policeDepartment?.findBase(baseId)
            } // K9 PoliceCar
        }
        if (base == null || base.staff < staffs) {
            return false
        }
        if (type == VehicleType.EMERGENCY_DOCTOR_CAR && base is Hospital && base.doctors == 0) {
            return false
        }
        if (type == VehicleType.K9_POLICE_CAR && base is PoliceStation && base.dogs == 0) {
            return false
        }
        val newVehicle = Vehicle(id, type, requireNotNull(base), staffs, height, null)
        base.addVehicle(newVehicle)
        vehicleidlist[id] = newVehicle
        listBases.replace(base, false, true)
        return res
    }

    private fun parseAmbulance(id: Int, baseId: Int, staffs: Int, height: Int): Boolean {
        var res = true
        val base = EMCC.ambulanceDepartment?.findBase(baseId) ?: return false
        val newVehicle = Ambulance(id, base, staffs, height, null, false)
        base.addVehicle(newVehicle)
        vehicleidlist[id] = newVehicle
        listBases.replace(base, false, true)
        if (base.staff < staffs) {
            res = false
        }
        return res
    }

    private fun parseFireTruckLadder(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int, height: Int): Boolean {
        var res = true
        val ladderLength = currVehicle.getInt(LADDERLENGTH)
        val ladder40 = ladderLength >= LADDER_REFERENCE
        val base = EMCC.fireDepartment?.findBase(baseId) ?: return false
        val newVehicle = FireTruckLadder(id, base, staffs, height, null, ladder40)
        base.addVehicle(newVehicle)
        vehicleidlist[id] = newVehicle
        listBases.replace(base, false, true)
        if (base.staff < staffs) {
            res = false
        }
        return res
    }

    private fun parseFireTruckWater(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int, height: Int): Boolean {
        var res = true
        val waterCapacity = currVehicle.getInt(WATERCAPACITY)
        if (waterCapacity != WATER_LITTLE && waterCapacity != WATER_MIDDLE && waterCapacity != WATER_BIG) res = false
        val base = EMCC.fireDepartment?.findBase(baseId) ?: return false
        val newVehicle = FireTruckWater(id, base, staffs, height, null, waterCapacity)
        base.addVehicle(newVehicle)
        vehicleidlist[id] = newVehicle
        listBases.replace(base, false, true)
        if (base.staff < staffs) {
            res = false
        }
        return res
    }

    private fun parsePoliceCar(currVehicle: JSONObject, id: Int, baseId: Int, staffs: Int, height: Int): Boolean {
        var res = true
        val crimCapacity = currVehicle.getInt(CRIMINALCAPACITY)
        val base = EMCC.policeDepartment?.findBase(baseId) ?: return false
        val newVehicle = PoliceCar(id, base, staffs, height, null, crimCapacity, 0)
        base.addVehicle(newVehicle)
        vehicleidlist[id] = newVehicle
        listBases.replace(base, false, true)
        if (base.staff < staffs) {
            res = false
        }
        return res
    }

    /**
     * function to parse Bases
     */
    fun parseBases(): Boolean {
        val baseidlist = mutableListOf<Int>()
        var res = true
        val fireDepartment = FireDepartment()
        val policeDepartment = PoliceDepartment()
        val ambulanceDepartment = AmbulanceDepartment()
        val jsonObject = JSONObject(jsonStringConfig1)
        val schem = getSchema(JsonParser::class.java, "assets.schema")
        try {
            schem?.validate(jsonObject)
        } catch (e: ValidationException) {
            e.message
            return false
        }

        val basesArray = jsonObject.getJSONArray("bases")
        for (i in 0 until basesArray.length()) {
            val currBase = basesArray.getJSONObject(i)
            val id = currBase.getInt(ID)
            val locationId = currBase.getInt("location")
            val location = gm.getVertexFromId(locationId) ?: return false
            if (baseidlist.contains(id) || location.base != null) {
                return false
            }
            baseidlist.add(id)
            val baseType = currBase.getString("baseType")
            val staffs = currBase.getInt("staff")
            var newBase: Base? = null
            val utils = JsonParserUtils()
            res = res && utils.okib(baseType, currBase)
            when (baseType) {
                "FIRE_STATION" -> {
                    newBase = Base(id, staffs, location, mutableListOf())
                    location.base = newBase
                    fireDepartment.addBase(newBase)
                    fir = true
                }

                "POLICE_STATION" -> {
                    val dogs = currBase.getInt(DOGS) // create Police Station
                    newBase = PoliceStation(id, staffs, location, mutableListOf(), dogs)
                    location.base = newBase
                    policeDepartment.addBase(newBase)
                    pol = true
                }

                "HOSPITAL" -> {
                    val doctor = currBase.getInt(DOCTORS)
                    newBase = Hospital(id, staffs, location, mutableListOf(), doctor)
                    location.base = newBase
                    ambulanceDepartment.addBase(newBase)
                    hos = true
                }
            }
            listBases[requireNotNull(newBase)] = false
            EMCC.policeDepartment = policeDepartment
            EMCC.fireDepartment = fireDepartment
            EMCC.ambulanceDepartment = ambulanceDepartment
        }
        return res && hos && pol && fir
    }

    /**
     * function to parse Emergencies
     */
    fun parseEmergency(): Boolean {
        val emgidlist = mutableListOf<Int>()
        val res = true
        val jsonObject = JSONObject(jsonStringConfig2)
        val schem = getSchema(JsonParser::class.java, "simulation.schema")
        try {
            schem?.validate(jsonObject)
        } catch (e: ValidationException) {
            e.message
            return false
        }
        val emerArray = jsonObject.getJSONArray("emergencyCalls")
        for (i in 0 until emerArray.length()) {
            val currEmer = emerArray.getJSONObject(i)
            val id = currEmer.getInt(ID)
            if (emgidlist.contains(id)) {
                return false
            }
            emgidlist.add(id)
            val tick = currEmer.getInt("tick")
            val village = currEmer.getString("village")
            val roadName = currEmer.getString("roadName")
            val road = gm.getRoad(village, roadName) ?: return false
            val type: EmergencyType = when (currEmer.getString("emergencyType")) {
                "FIRE" -> EmergencyType.FIRE
                "ACCIDENT" -> EmergencyType.ACCIDENT
                "CRIME" -> EmergencyType.CRIME
                else -> EmergencyType.MEDICAL
            }
            val severity = currEmer.getInt("severity")
            val handleTime = currEmer.getInt("handleTime")
            val maxDuration = currEmer.getInt("maxDuration")
            // resources
            if (maxDuration <= handleTime) {
                return false
            }
            val resources = resourcesParse(type, severity)
            val newEmergency = Emergency(id, tick, road, type, severity, handleTime, maxDuration, resources)
            Simulation.addEmergency(newEmergency)
        }
        return res
    }

    private fun resourcesParse(type: EmergencyType, severity: Int): Resource {
        val resourcesFactory = ResourceFactory() // resourceFactory
        return when (type) {
            EmergencyType.ACCIDENT -> resourcesFactory.createAccidentResources(severity)
            EmergencyType.CRIME -> resourcesFactory.createCrimeResources(severity)
            EmergencyType.FIRE -> resourcesFactory.createFireResources(severity)
            EmergencyType.MEDICAL -> resourcesFactory.createMedicalResources(severity)
        }
    }

    /**
     * funtion to parse Events
     */
    fun parseEvents(): Boolean {
        var res = true
        val eventidlist = mutableListOf<Int>()
        val jsonObject = JSONObject(jsonStringConfig2)
        val eventArray = jsonObject.getJSONArray("events")
        for (i in 0 until eventArray.length()) {
            val currEvent = eventArray.getJSONObject(i)
            val id = currEvent.getInt(ID)
            if (eventidlist.contains(id)) {
                return false
            }
            eventidlist.add(id)
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
        return res
    }

    private fun parseVehicleUnavailableEvent(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        if (currEvent.has(ROADTYPES) || currEvent.has(FACTOR)) {
            res = false
        }
        if (currEvent.has(SOURCE) || currEvent.has(TARGET) || currEvent.has(ONEWAYSTREET)) {
            res = false
        }
        val vehicleId = currEvent.getInt(VEHICLEID)
        // need list of bases
        val vehicle: Vehicle
        vehicle = vehicleidlist[vehicleId] ?: return false
        val newEvent = VehicleUnavailableEvent(id, tick, duration, vehicle)
        Simulation.addEvent(newEvent)
        return res
    }

    private fun parseRoadClosureEvent(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        if (currEvent.has(ROADTYPES) || currEvent.has(FACTOR)) {
            res = false
        }
        if (currEvent.has(ONEWAYSTREET) || currEvent.has(VEHICLEID)) {
            res = false
        }
        val sourceVertexId = currEvent.getInt(SOURCE)
        val targetVertexId = currEvent.getInt(TARGET)
        val sourceVertex = gm.getVertexFromId(sourceVertexId) ?: return false
        val targetVertex = gm.getVertexFromId(targetVertexId) ?: return false
        val road = gm.getRoad(sourceVertex, targetVertex) ?: return false
        val newEvent = RoadClosureEvent(id, tick, duration, road)
        Simulation.addEvent(newEvent)
        return res
    }

    private fun parseConstructionSite(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        if (currEvent.has(ROADTYPES) || currEvent.has(VEHICLEID)) {
            res = false
        }
        val sourceVertexId = currEvent.getInt(SOURCE)
        val targetVertexId = currEvent.getInt(TARGET)
        val oneWayStreet = currEvent.getBoolean(ONEWAYSTREET)
        val factor = currEvent.getInt(FACTOR)
        val sourceVertex = gm.getVertexFromId(sourceVertexId) ?: return false
        val targetVertex = gm.getVertexFromId(targetVertexId) ?: return false
        val road = gm.getRoad(sourceVertex, targetVertex) ?: return false
        val newEvent = ConstructionSiteEvent(
            id,
            tick,
            duration,
            oneWayStreet,
            road,
            factor,
            sourceVertexId,
            targetVertexId
        )
        Simulation.addEvent(newEvent)
        return res
    }

    private fun parseTrafficJAM(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        if (currEvent.has(ROADTYPES) || currEvent.has(ONEWAYSTREET) || currEvent.has(VEHICLEID)) {
            res = false
        }
        val factor = currEvent.getInt(FACTOR)
        val sourceVertexId = currEvent.getInt(SOURCE)
        val targetVertexId = currEvent.getInt(TARGET)
        val sourceVertex = gm.getVertexFromId(sourceVertexId) ?: return false
        val targetVertex = gm.getVertexFromId(targetVertexId) ?: return false
        val road = gm.getRoad(sourceVertex, targetVertex) ?: return false
        val newEvent = TrafficJamEvent(id, tick, duration, road, factor)
        Simulation.addEvent(newEvent)
        return res
    }

    private fun parseRushHour(currEvent: JSONObject, id: Int, tick: Int, duration: Int): Boolean {
        var res = true
        if (currEvent.has(SOURCE) || currEvent.has(TARGET)) {
            res = false
        }
        if (currEvent.has(ONEWAYSTREET) || currEvent.has(VEHICLEID)) {
            res = false
        }
        val roadList = mutableListOf<Road>()
        val roadTypes: MutableList<PrimaryRoadType> = parseRoadList(currEvent.getJSONArray(ROADTYPES))
        val factor = currEvent.getInt(FACTOR)
        for (type in roadTypes) {
            roadList.addAll(gm.getListRoad(type))
        }
        if (roadList.size != roadList.distinct().count()) {
            return false
        }
        val newEvent = RushHourEvent(id, tick, duration, roadList, factor)
        Simulation.addEvent(newEvent)
        return res
    }

    private fun parseVehicleType(s: Any): VehicleType? {
        return when (s) {
            "POLICE_CAR" -> VehicleType.POLICE_CAR
            "K9_POLICE_CAR" -> VehicleType.K9_POLICE_CAR
            "POLICE_MOTORCYCLE" -> VehicleType.POLICE_MOTORCYCLE
            "FIRE_TRUCK_WATER" -> VehicleType.FIRE_TRUCK_WATER
            "FIRE_TRUCK_TECHNICAL" -> VehicleType.FIRE_TRUCK_TECHNICAL
            "FIRE_TRUCK_LADDER" -> VehicleType.FIRE_TRUCK_LADDER
            "FIREFIGHTER_TRANSPORTER" -> VehicleType.FIREFIGHTER_TRANSPORTER
            "AMBULANCE" -> VehicleType.AMBULANCE
            "EMERGENCY_DOCTOR_CAR" -> VehicleType.EMERGENCY_DOCTOR_CAR
            else -> {
                null
            }
        }
    }

    private fun parseRoadList(arr: JSONArray): MutableList<PrimaryRoadType> {
        val list = mutableListOf<PrimaryRoadType>()
        for (i in 0 until arr.length()) {
            val e = arr.getString(i)
            when (e) {
                "MAIN_STREET" -> list.add(PrimaryRoadType.MAINSTREET)
                "SIDE_STREET" -> list.add(PrimaryRoadType.SIDESTREET)
                "COUNTY_ROAD" -> list.add(PrimaryRoadType.COUNTYROAD)
            }
        }
        return list
    }

    companion object {
        const val LADDER_REFERENCE = 40 // because magic number
        const val SOURCE = "source" // because duplicates
        const val TARGET = "target" // because duplicates
        const val ID = "id" // because duplications for keywords
        const val WATER_LITTLE = 600
        const val WATER_MIDDLE = 1200
        const val WATER_BIG = 2400
        const val FACTOR = "factor"
        const val LADDERLENGTH = "ladderLength"
        const val WATERCAPACITY = "waterCapacity"
        const val CRIMINALCAPACITY = "criminalCapacity"
        const val DOCTORS = "doctors"
        const val DOGS = "dogs"
        const val ROADTYPES = "roadTypes"
        const val ONEWAYSTREET = "oneWayStreet"
        const val VEHICLEID = "vehicleID"
    }
}
