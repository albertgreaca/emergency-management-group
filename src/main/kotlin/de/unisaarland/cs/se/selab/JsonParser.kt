package de.unisaarland.cs.se.selab

import org.json.JSONObject
import java.io.File

class JsonParser(private val gm: GraphMap, private val file1:File, private val file2:File) {


    fun parseVehicles():Boolean {
        val jsonObject: JSONObject = JSONObject(file1.readText())
        val vehiclesObject = jsonObject.getJSONArray("Vehicles")
        for (i in 0 until vehiclesObject.length()) {
            val currVehicle = vehiclesObject.getJSONObject(i)
            val id = currVehicle.getInt("id")
            if (id<0) return false
            val baseId = currVehicle.getInt("baseID")
            val vehicleType = currVehicle.getString("VehicleType")
            val vehicleHeight = currVehicle.getInt("vehicleHeight")
            val staffCapacity = currVehicle.getInt("staffCapacity")
            when (vehicleType) {
                "POLICE_CAR" -> {
                    val crimCapacity = currVehicle.getInt("criminalCapacity") }// create PoliceCar and add to BAse
                "FIRE_TRUCK_WATER" -> {
                    val waterCapacity = currVehicle.getInt("waterCapacity") }
                    // create FireTruckWater
                "FIRE_TRUCK_LADDER" -> {val ladderLength = currVehicle.getInt("ladderLength")} //create FireTruck
            }
            //create Vehicle
        }
        return true
    }



    fun parseBases(): Boolean {
        val jsonObject: JSONObject = JSONObject(file1.readText())
        val basesObject = jsonObject.getJSONArray("Bases")
        for (i in 0 until basesObject.length()) {
            val currBase = basesObject.getJSONObject(i)
            val id = currBase.getInt("id")
            if (id<0) return false
            val baseType = currBase.getString("baseType")
            val locationId = currBase.getInt("location")
            if (locationId<0) return false
            val location = gm.getVertex(locationId) ?: return false
            val staffs = currBase.getInt("staffs")
            if (staffs<1) return false
            when (baseType) {
                "FIRE_STATION" -> Base(id, staffs, location, mutableListOf())
                "POLICE_STATION" -> {
                    val dogs = currBase.getInt("dogs") //create Police Station
                }

                "HOSPITAL" -> {
                    val doctor = currBase.getInt("doctors") //create Hospital}
                }
            }
        }
        return true
    }

    fun parseEmergency():Boolean{
        val jsonObject: JSONObject = JSONObject(file2.readText())
        val emerArray = jsonObject.getJSONArray("EmergencyCalls")
        for (i in 0 until emerArray.length()){
            val currEmer = emerArray.getJSONObject(i)
            val id = currEmer.getInt("id")
            val tick = currEmer.getInt("tick")
            val village = currEmer.getString("village")
            val roadName = currEmer.getString("roadName")
            val road = gm.getRoad(village, roadName) ?: return false
            val type:EmergencyType = currEmer.get("emergencyType") as EmergencyType
            val severity = currEmer.getInt("severity")
            val handleTime = currEmer.getInt("handleTime")
            val maxDuration = currEmer.getInt("maxDuration")
            val resources = Resource(mutableListOf(),0,0,0,0) //resourceFactory

            Emergency(id, tick, road, type, severity, handleTime, maxDuration, resources)
        }
        return true
    }
}
