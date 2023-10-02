import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class ReallocationTest {

    @Test
    fun simpletest1() {
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
        val emless = Emergency(1, 1, road, EmergencyType.FIRE, 1, 2, 20, res)
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        for (vehicle in b.vehicles) {
            vehicle.available = false
            vehicle.targetEmergency = emless
            vehicle.position = Dijkstra.dijkstraHeight(2, road, vehicle.vehicleHeight)
        }
        b.reallocateResources(em)
        val vehicle1 = b.vehicles[0]
        assertTrue(vehicle1.targetEmergency == em)
        assertTrue(em.assignedVehicles[0] == vehicle1)
    }
}
