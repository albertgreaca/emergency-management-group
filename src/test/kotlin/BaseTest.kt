import de.unisaarland.cs.se.selab.Emergency
import de.unisaarland.cs.se.selab.EmergencyType
import de.unisaarland.cs.se.selab.GraphMap
import de.unisaarland.cs.se.selab.JsonParser
import de.unisaarland.cs.se.selab.MapParser
import de.unisaarland.cs.se.selab.Resource
import de.unisaarland.cs.se.selab.VehicleType
import org.junit.jupiter.api.BeforeEach
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
        val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
    }

    /*
    @Test
    fun watertrucktest1() {
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
        val vehicleList = mutableListOf<VehicleType>(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // here we have an index out of bound error
        var vehicles = b.vehicles
        val vehicle1 = vehicles.get(0)
        val vehicle2 = vehicles.get(3)
        assertTrue(em.resources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles.get(0))
        assertTrue(vehicle2 == em.assignedVehicles.get(1))
    }
     */
}
