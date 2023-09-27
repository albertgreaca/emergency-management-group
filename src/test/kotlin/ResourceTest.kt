import de.unisaarland.cs.se.selab.Resource
import de.unisaarland.cs.se.selab.VehicleType
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResourceTest {

    @Test
    fun isEmpty1() {
        val res = Resource(mutableListOf(), 0, 0, 0, null)
        assertTrue(res.isEmpty())
    }

    @Test
    fun isEmpty2() {
        val res = Resource(mutableListOf(), 0, 0, 0, 0)
        assertTrue(res.isEmpty())
    }

    @Test
    fun isEmptyfalse() {
        val res = Resource(mutableListOf(VehicleType.AMBULANCE), 1, 4, 5, 0)
        assertFalse(res.isEmpty())
    }

    @Test
    fun policeresources() {
        val vehicleList =
            mutableListOf<VehicleType>(
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.K9_POLICE_CAR,
                VehicleType.K9_POLICE_CAR, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE,
                VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.FIREFIGHTER_TRANSPORTER
            )
        var res = Resource(vehicleList, 10, 5, 32, null)
        val policeres = res.filterPoliceResources()
        val testres =
            Resource(
                mutableListOf<VehicleType>(
                    VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                    VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.K9_POLICE_CAR,
                    VehicleType.K9_POLICE_CAR, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE
                ),
                0,
                5,
                0,
                null
            )
        assertTrue(policeres.isEqual(testres))
    }

    @Test
    fun hospitalrequest() {
        val vehicleList =
            mutableListOf<VehicleType>(
                VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.AMBULANCE, VehicleType.EMERGENCY_DOCTOR_CAR, VehicleType.EMERGENCY_DOCTOR_CAR,
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL
            )
        var res = Resource(vehicleList, 10, 5, 32, null)
        val hospitalres = res.filterAmbulanceResources()
        val testres =
            Resource(
                mutableListOf<VehicleType>(
                    VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE,
                    VehicleType.AMBULANCE,
                    VehicleType.EMERGENCY_DOCTOR_CAR,
                    VehicleType.EMERGENCY_DOCTOR_CAR
                ),
                0,
                0,
                32,
                null
            )
        assertTrue(hospitalres.isEqual(testres))
    }

    @Test
    fun firerequest() {
        val vehicleList =
            mutableListOf<VehicleType>(
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL, VehicleType.POLICE_MOTORCYCLE, VehicleType.POLICE_MOTORCYCLE,
                VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_CAR,
                VehicleType.AMBULANCE, VehicleType.AMBULANCE, VehicleType.AMBULANCE,
                VehicleType.EMERGENCY_DOCTOR_CAR
            )
        var res = Resource(vehicleList, 10, 5, 32, null)
        val fireres = res.filterFireResources()
        val testres = Resource(
            mutableListOf<VehicleType>(
                VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL,
                VehicleType.FIRE_TRUCK_TECHNICAL
            ),
            10,
            0,
            0,
            null
        )
        assertTrue(fireres.isEqual(testres))
    }
}
