package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.json.JSONObject

/**
 * detekt
 */
class JsonParserUtils {
    /**
     * detekt
     */
    fun oki(vehicleType: VehicleType, currVehicle: JSONObject): Boolean {
        if (vehicleType == VehicleType.EMERGENCY_DOCTOR_CAR) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                return false
            }
        }
        if (vehicleType == VehicleType.FIREFIGHTER_TRANSPORTER) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                return false
            }
        }
        if (vehicleType == VehicleType.FIRE_TRUCK_TECHNICAL) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                return false
            }
        }
        return oki2(vehicleType, currVehicle)
    }

    /**
     * detekt
     */
    fun oki2(vehicleType: VehicleType, currVehicle: JSONObject): Boolean {
        if (vehicleType == VehicleType.POLICE_MOTORCYCLE) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                return false
            }
        }
        if (
            currVehicle.has(JsonParser.LADDERLENGTH) ||
            currVehicle.has(JsonParser.WATERCAPACITY) ||
            currVehicle.has(JsonParser.CRIMINALCAPACITY)
        ) {
            return false
        }
        return true
    }

    /**
     * detekt
     */
    fun okib(baseType: String, currBase: JSONObject): Boolean {
        if (baseType == "FIRE_STATION") {
            if (currBase.has(JsonParser.DOCTORS) || currBase.has(JsonParser.DOGS)) {
                return false
            }
        }
        if (baseType == "POLICE_STATION") {
            if (currBase.has(JsonParser.DOCTORS)) {
                return false
            }
        }
        if (baseType == "HOSPITAL") {
            if (currBase.has(JsonParser.DOGS)) {
                return false
            }
        }
        return true
    }
}
