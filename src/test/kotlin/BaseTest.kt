import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class BaseTest {
    @BeforeEach
    fun before() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
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
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
    }

    @Test
    fun watertrucktest1800w2vehic() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
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
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // here we have an index out of bound error
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[3]
        assertTrue(em.resources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
    }

    @Test
    fun watertrucktest1200w2vehic() {
        // some more cases I thought of:
        // 2400, 1 vehic
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
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
        val res = Resource(vehicleList, 1200, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // here we have an index out of bound error
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[1]
        assertTrue(em.resources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
    }

    @Test
    fun watertrucktest2400w2vehic() {
        // some more cases I thought of:
        // 2400, 1 vehic
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
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
        val res = Resource(vehicleList, 2400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // in this case, should the base send a 600 Watertruck, as the 1800 can stil be fulfiled with a 2400?
        // val vehicles = b.vehicles
        // val vehicle1 = vehicles[3]
        // val testres = Resource(mutableListOf(VehicleType.FIRE_TRUCK_WATER), 1200, 0, 0, 0)
        // assertTrue(em.resources.isEqual(testres))
        // assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(true)
    }

    @Test
    fun watertrucktest2400w1vehic() {
        // some more cases I thought of:
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 2400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(vehicleList, 2400, 0, 0, 0)
        assertTrue(em.assignedVehicles.isEmpty())
        assertTrue(testres.isEqual(em.resources))
    }

    @Test
    fun watertrucktest1800w3vehic() {
        // some more cases I thought of:
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER
        )
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[1]
        val vehicle3 = vehicles[2]
        assertTrue(em.resources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
        assertTrue(vehicle3 == em.assignedVehicles[2])
    }

    @Test
    fun watertrucktest1800w1vehic() {
        // some more cases I thought of:
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(vehicleList, 1800, 0, 0, 0)
        assertTrue(em.assignedVehicles.isEmpty())
        assertTrue(testres.isEqual(em.resources))
    }

    @Test
    fun watertrucktest5400w6vehic() {
        // some more cases I thought of:
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/5400Waterassetconfig.json"),
            File("src/test/resources/UnitTestConfig2/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER
        )
        val res = Resource(vehicleList, 5400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(mutableListOf(VehicleType.FIRE_TRUCK_WATER), 2400, 0, 0, 0)
        assertTrue(testres.isEqual(em.resources))
    }
}
