package de.unisaarland.cs.se.selab.resources

import de.unisaarland.cs.se.selab.vehicles.VehicleType

/**
 * creates Resources in the parser for Emergencies
 */
class ResourceFactory {
    /**
     * @return Resources for Fire Emergencies
     */
    fun createFireResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(
                mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER),
                WATERCONSTANTFIRE1,
                0,
                0,
                null
            )
        } else if (severity == 2) {
            val vehicleList = mutableListOf<VehicleType>(
                VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_LADDER,
                VehicleType.FIREFIGHTER_TRANSPORTER,
                VehicleType.AMBULANCE
            )
            return Resource(vehicleList, WATERCONSTANTFIRE2, 0, 1, LADDERCONSTANTFIRE2)
        } else if (severity == 3) {
            val vehicleList = mutableListOf<VehicleType>(
                VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIREFIGHTER_TRANSPORTER,
                VehicleType.FIREFIGHTER_TRANSPORTER, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.EMERGENCY_DOCTOR_CAR
            )
            return Resource(vehicleList, WATERCONSTANTFIRE3, 0, PATIENTCONSTANTFIRE3, LADDERCONSTANTFIRE3)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    /**
     * @return Resources for Accident Emergency
     */
    fun createAccidentResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_TECHNICAL), 0, 0, 0, null)
        } else if (severity == 2) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.FIRE_TRUCK_TECHNICAL,
                    VehicleType.FIRE_TRUCK_TECHNICAL,
                    VehicleType.POLICE_MOTORCYCLE,
                    VehicleType.POLICE_CAR,
                    VehicleType.AMBULANCE
                )
            return Resource(vehicleList, 0, 0, 1, null)
        } else if (severity == 3) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL,
                    VehicleType.FIRE_TRUCK_TECHNICAL,
                    VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE,
                    VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                    VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                    VehicleType.EMERGENCY_DOCTOR_CAR
                )
            return Resource(vehicleList, 0, 0, 2, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    /**
     * @return Resources for Crime Emergencies
     */
    fun createCrimeResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.POLICE_CAR), 0, 1, 0, null)
        } else if (severity == 2) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.POLICE_CAR,
                    VehicleType.POLICE_CAR,
                    VehicleType.POLICE_CAR,
                    VehicleType.POLICE_CAR,
                    VehicleType.K9_POLICE_CAR,
                    VehicleType.AMBULANCE
                )
            return Resource(vehicleList, 0, CRIMINALAMOUNTCRIME2, 0, null)
        } else if (severity == 3) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                    VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.K9_POLICE_CAR,
                    VehicleType.K9_POLICE_CAR, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE,
                    VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.FIREFIGHTER_TRANSPORTER
                )
            return Resource(vehicleList, 0, CRIMINALAMOUNTCRIME3, 1, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    // patientamount seems unlikely wtf??
    /**
     * @return Resources needed for Medical Emergencies
     */
    fun createMedicalResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.AMBULANCE), 0, 0, 0, null)
        } else if (severity == 2) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE,
                    VehicleType.EMERGENCY_DOCTOR_CAR
                )
            return Resource(vehicleList, 0, 0, 2, null)
        } else if (severity == 3) {
            val vehicleList =
                mutableListOf<VehicleType>(
                    VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR, VehicleType.EMERGENCY_DOCTOR_CAR,
                    VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL
                )
            return Resource(vehicleList, 0, 0, PATIENTCONSTANTMED3, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    companion object {
        const val WATERCONSTANTFIRE1 = 1200
        const val WATERCONSTANTFIRE2 = 3000
        const val LADDERCONSTANTFIRE2 = 30
        const val WATERCONSTANTFIRE3 = 5400
        const val PATIENTCONSTANTFIRE3 = 2
        const val PATIENTCONSTANTMED3 = 5
        const val LADDERCONSTANTFIRE3 = 40
        const val CRIMINALAMOUNTCRIME2 = 4
        const val CRIMINALAMOUNTCRIME3 = 8
    }
}
