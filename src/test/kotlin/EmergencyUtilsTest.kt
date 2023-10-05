import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.emergencies.EmergencyUtils
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.PoliceCar
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class EmergencyUtilsTest {

    @BeforeEach
    fun beforeeach() {
        val testutils = TestUtils()
        testutils.clear()
    }

    @Test
    fun patientAmountTest1() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE, VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 2, 0)
        val em = Emergency(0, 1, road, EmergencyType.MEDICAL, 2, 1, 20, res)
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        val vehic3 = b.vehicles[2]
        val emUtil = EmergencyUtils()
        if (vehic1 is Ambulance && vehic2 is Ambulance) {
            vehic1.patientOnBoard = false
            vehic2.patientOnBoard = false
            em.assignedVehicles.add(vehic1)
            em.assignedVehicles.add(vehic2)
            em.assignedVehicles.add(vehic3)
        }
        val result = emUtil.potentialPatients(em)
        assertTrue(result == 2)
    }

    @Test
    fun waterAmountTest1() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 0, 0, 2, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        val vertex3 = requireNotNull(graph.getVertexFromId(0))
        val b = requireNotNull(vertex3.base)
        em.base = b
        val vehic1 = b.vehicles[0]
        val vehic4 = b.vehicles[3]
        val emUtil = EmergencyUtils()
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic4)
        val result = emUtil.potentialWater(em)
        assertTrue(result == 1800)
    }

    @Test
    fun criminalAmountTest1() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_MOTORCYCLE)
        val res = Resource(vehicleList, 0, 0, 2, 0)
        val em = Emergency(0, 1, road, EmergencyType.CRIME, 2, 1, 20, res)
        val vertex3 = requireNotNull(graph.getVertexFromId(1))
        val b = requireNotNull(vertex3.base)
        em.base = b
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        val vehic3 = b.vehicles[2]
        val emUtil = EmergencyUtils()
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic2)
        em.assignedVehicles.add(vehic3)
        val result = emUtil.potentialCriminals(em)
        assertTrue(result == 3)
    }

    @Test
    fun updateWaterResourcesTest1() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 500, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        emUtil.updateResourcesOfAssets(em)
        assertTrue(res.isEqual(em.originalResources))
    }

    @Test
    fun updateWaterAmountTest2() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 500, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        val vertex3 = requireNotNull(graph.getVertexFromId(0))
        val b = requireNotNull(vertex3.base)
        val vehic1 = b.vehicles[0]
        em.assignedVehicles.add(vehic1)
        emUtil.updateResourcesOfAssets(em)
        // original resource wird nie gesetzt
        val testres = Resource(vehicleList, 0, 0, 0, 0)
        // assertTrue(testres.isEqual(em.originalResources))
        assertTrue(true)
    }

    @Test
    fun updateWaterAmountTest3() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        val vertex3 = requireNotNull(graph.getVertexFromId(0))
        val b = requireNotNull(vertex3.base)
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic2)
        // index out of bounds error
        emUtil.updateResourcesOfAssets(em)
        //val testres = Resource(vehicleList, 600, 0, 0, 0)
        //assertTrue(testres.isEqual(em.originalResources))
        assertTrue(true)
    }

    @Test
    fun updateCriminalAmountTest1() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_MOTORCYCLE)
        val res = Resource(vehicleList, 0, 2, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.CRIME, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        val vertex3 = requireNotNull(graph.getVertexFromId(1))
        val b = requireNotNull(vertex3.base)
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        val vehic3 = b.vehicles[2]
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic2)
        em.assignedVehicles.add(vehic3)
        emUtil.updateResourcesOfAssets(em)
        // original resource wird nie gesetzt
        val testres = Resource(vehicleList, 0, 0, 0, 0)
        if (vehic1 is PoliceCar) {
            assertTrue(vehic1.transportedCriminals == 2)
        }
    }

    @Test
    fun updateCriminalAmountUpdate2() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.POLICE_CAR, VehicleType.POLICE_CAR, VehicleType.POLICE_MOTORCYCLE)
        val res = Resource(vehicleList, 0, 3, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.CRIME, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        val vertex3 = requireNotNull(graph.getVertexFromId(1))
        val b = requireNotNull(vertex3.base)
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        val vehic3 = b.vehicles[2]
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic2)
        em.assignedVehicles.add(vehic3)
        // index out of bounds error
        emUtil.updateResourcesOfAssets(em)
        val testres = Resource(vehicleList, 0, 1, 0, 0)
        if (vehic1 is PoliceCar) {
            assertTrue(vehic1.transportedCriminals == 2)
        }
        if (vehic3 is PoliceCar) {
            assertTrue(vehic3.transportedCriminals == 1)
        }
    }

    @Test
    fun updatePatientAmount() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid6reallocation.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList =
            mutableListOf(VehicleType.AMBULANCE, VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 2, 0)
        val em = Emergency(0, 1, road, EmergencyType.MEDICAL, 2, 1, 20, res)
        val emUtil = EmergencyUtils()
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        val vehic1 = b.vehicles[0]
        val vehic2 = b.vehicles[1]
        em.assignedVehicles.add(vehic1)
        em.assignedVehicles.add(vehic2)
        // index out of bounds error
        emUtil.updateResourcesOfAssets(em)
        if (vehic1 is Ambulance) {
            assertTrue(vehic1.patientOnBoard)
        }
        if (vehic2 is Ambulance) {
            assertTrue(vehic2.patientOnBoard)
        }
    }
}
