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
    }

    fun createAccidentResources(severity: Int): Resource {
        var waterNeeded = 0
        var criminalNeeded = 0
        var patientNeeded = 0
        var vehiclesNeeded = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_TECHNICAL)
        // T0D0
    }

    fun createCrimeResources(severity: Int): Resource {
        var waterNeeded = 0
        var criminalNeeded = 1
        var patientNeeded = 0
        var vehiclesNeeded = mutableListOf<VehicleType>(VehicleType.POLICE_CAR)
    // T0D0
    }

    fun createMedicalResources(severity: Int): Resource {
        var waterNeeded = 0
        var criminalNeeded = 0
        var patientNeeded = 0
        var vehiclesNeeded = mutableListOf<VehicleType>(VehicleType.AMBULANCE)
    // T0D0
    }
}