package de.unisaarland.cs.se.selab

class ResourceFactory {

    fun createFireResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER), 1200, 0, 0, null)
        } else if (severity == 2) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIREFIGHTER_TRANSPORTER,
                VehicleType.AMBULANCE)
            return Resource(vehicleList, 3000, 0, 1, 30)
        } else if (severity == 3) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
                VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIREFIGHTER_TRANSPORTER,
                VehicleType.FIREFIGHTER_TRANSPORTER, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR)
            return Resource(vehicleList, 5400, 0, 2, 40)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    fun createAccidentResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_TECHNICAL), 0, 0,0, null)
        } else if (severity == 2) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_CAR,
                VehicleType.AMBULANCE)
            return Resource(vehicleList, 0, 0, 1, null)
        } else if (severity == 3) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR)
            return  Resource(vehicleList, 0, 0, 2, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }

    fun createCrimeResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.POLICE_CAR), 0, 1, 0, null)
        } else if (severity == 2) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.K9_POLICE_CAR, VehicleType.AMBULANCE)
            return Resource(vehicleList, 0, 4, 0, null)
        } else if (severity == 3) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.K9_POLICE_CAR, VehicleType.K9_POLICE_CAR,
                VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.FIREFIGHTER_TRANSPORTER)
            return Resource(vehicleList, 0, 8, 1, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }
    //patientamount seems unlikely wtf??
    fun createMedicalResources(severity: Int): Resource {
        if (severity == 1) {
            return Resource(mutableListOf<VehicleType>(VehicleType.AMBULANCE), 0, 0, 0, null)
        } else if (severity == 2) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR)
            return Resource(vehicleList, 0, 0, 2, null)
        } else if (severity == 3) {
            val vehicleList = mutableListOf<VehicleType>(VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR, VehicleType.EMERGENCY_DOCTOR_CAR, VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL)
            return Resource(vehicleList, 0, 0, 5, null)
        }
        return Resource(mutableListOf<VehicleType>(), 0, 0, 0, null)
    }
}
