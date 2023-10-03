import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.resources.ResourceFactory
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class ResourceFactoryTest {
    @Test
    fun fire1test() {
        val neededVehics = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val compareres = Resource(neededVehics, 1200, 0, 0, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createFireResources(1)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun fire2test() {
        val neededVehicles = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_LADDER,
            VehicleType.FIREFIGHTER_TRANSPORTER,
            VehicleType.AMBULANCE
        )
        val compareres = Resource(neededVehicles, 3000, 0, 1, 30)
        val resFac = ResourceFactory()
        val testres = resFac.createFireResources(2)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun fire3test() {
        val neededVehicles = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_LADDER,
            VehicleType.FIRE_TRUCK_LADDER, VehicleType.FIREFIGHTER_TRANSPORTER,
            VehicleType.FIREFIGHTER_TRANSPORTER, VehicleType.AMBULANCE,
            VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR
        )
        val compareres = Resource(neededVehicles, 5400, 0, 2, 40)
        val resFac = ResourceFactory()
        val testres = resFac.createFireResources(3)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun accident1test() {
        val neededVehics = mutableListOf(VehicleType.FIRE_TRUCK_TECHNICAL)
        val compareres = Resource(neededVehics, 0, 0, 0, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createAccidentResources(1)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun accident2test() {
        val neededVehics = mutableListOf(
            VehicleType.FIRE_TRUCK_TECHNICAL,
            VehicleType.FIRE_TRUCK_TECHNICAL,
            VehicleType.POLICE_MOTORCYCLE,
            VehicleType.POLICE_CAR,
            VehicleType.AMBULANCE
        )
        val compareres = Resource(neededVehics, 0, 0, 1, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createAccidentResources(2)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun accident3test() {
        val neededVehics = mutableListOf(
            VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL,
            VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL,
            VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_CAR,
            VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
            VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR
        )
        val compareres = Resource(neededVehics, 0, 0, 2, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createAccidentResources(3)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun crime1test() {
        val neededVehics = mutableListOf(VehicleType.POLICE_CAR)
        val compareres = Resource(neededVehics, 0, 1, 0, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createCrimeResources(1)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun crime2test() {
        val neededVehics =
            mutableListOf(
                VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR,
                VehicleType.K9_POLICE_CAR,
                VehicleType.AMBULANCE
            )
        val compareres = Resource(neededVehics, 0, 4, 0, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createCrimeResources(2)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun crime3test() {
        val neededVehics =
            mutableListOf(
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_MOTORCYCLE,
                VehicleType.POLICE_MOTORCYCLE, VehicleType.K9_POLICE_CAR,
                VehicleType.K9_POLICE_CAR, VehicleType.AMBULANCE,
                VehicleType.AMBULANCE, VehicleType.FIREFIGHTER_TRANSPORTER
            )
        val compareres = Resource(neededVehics, 0, 8, 1, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createCrimeResources(3)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun med1test() {
        val neededVehics = mutableListOf(VehicleType.AMBULANCE)
        val compareres = Resource(neededVehics, 0, 0, 0, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createMedicalResources(1)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun med2test() {
        val neededVehics = mutableListOf(VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR)
        val compareres = Resource(neededVehics, 0, 0, 2, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createMedicalResources(2)
        assertTrue(compareres.isEqual(testres))
    }

    @Test
    fun med3test() {
        val neededVehics =
            mutableListOf(
                VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.EMERGENCY_DOCTOR_CAR, VehicleType.EMERGENCY_DOCTOR_CAR,
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL
            )
        val compareres = Resource(neededVehics, 0, 0, 5, 0)
        val resFac = ResourceFactory()
        val testres = resFac.createMedicalResources(3)
        assertTrue(compareres.isEqual(testres))
    }
}
