import de.unisaarland.cs.se.selab.Resource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class BasisTesting {

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
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[1]
        val testres = Resource(vehicleList, 2400, 0,  0, 0)
        assertTrue(em.assignedVehicles.isEmpty())
        assertTrue(testres == em.resources)
    }

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
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
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
}
