package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.vehicles.VehicleType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONObject

/**
 * detekt
 */
class JsonParserUtils {
    private val logger = KotlinLogging.logger {}

    /**
     * detekt
     */
    fun oki(vehicleType: VehicleType, currVehicle: JSONObject): Boolean {
        if (vehicleType == VehicleType.EMERGENCY_DOCTOR_CAR) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                logger.error { "doctor car has wrong attributes, line 21" }
                return false
            }
        }
        if (vehicleType == VehicleType.FIREFIGHTER_TRANSPORTER) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                logger.error { "firefigther transporter has wrong attributes, line 30" }
                return false
            }
        }
        if (vehicleType == VehicleType.FIRE_TRUCK_TECHNICAL) {
            if (currVehicle.has(JsonParser.LADDERLENGTH) ||
                currVehicle.has(JsonParser.WATERCAPACITY) ||
                currVehicle.has(JsonParser.CRIMINALCAPACITY)
            ) {
                logger.error { "fire truck technical has wrong attributes, line 40" }
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
                logger.error { "police motorcycle has wrong attributes, line 56" }
                return false
            }
        }
        if (
            currVehicle.has(JsonParser.LADDERLENGTH) ||
            currVehicle.has(JsonParser.WATERCAPACITY) ||
            currVehicle.has(JsonParser.CRIMINALCAPACITY)
        ) {
            logger.error { "whatever is left has wrong attributes, line 65" }
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
                logger.error { "fire station has wrong attributes, line 77" }
                return false
            }
        }
        if (baseType == "POLICE_STATION") {
            if (currBase.has(JsonParser.DOCTORS)) {
                logger.error { "police station has wrong attributes, line 83" }
                return false
            }
        }
        if (baseType == "HOSPITAL") {
            if (currBase.has(JsonParser.DOGS)) {
                logger.error { "hospital has wrong attributes, line 89" }
                return false
            }
        }
        return true
    }
}
