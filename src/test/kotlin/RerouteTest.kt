import org.junit.jupiter.api.BeforeEach

/*import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.events.RoadClosureEvent
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.utils.Position
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue*/

class RerouteTest {
    val utils = TestUtils()

    @BeforeEach
    fun beforeEach() {
        utils.clear()
    }

    /*@Test
    fun rerouteTestnoRerouting() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid5.dot"))
        parse.parseMap()
        val vertex0 = requireNotNull(graph.getVertexFromId(0))
        val vertex1 = requireNotNull(graph.getVertexFromId(4))
        val vertex2 = requireNotNull(graph.getVertexFromId(6))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 0, 0)
        val emergency = Emergency(0, 1, road, EmergencyType.MEDICAL, 1, 1, 100, res)
        val base = Base(0, 20, vertex0, mutableListOf())
        val vehicle = Vehicle(0, VehicleType.AMBULANCE, base, 4, 1, null)
        base.addVehicle(vehicle)
        emergency.addVehicle(vehicle)
        vehicle.targetEmergency = emergency
        vehicle.position = Dijkstra.dijkstraHeight(vertex0.realid, emergency.road, vehicle.vehicleHeight)
        assertTrue(requireNotNull(vehicle.position).distance == 20)
        vehicle.move()
        assertTrue(requireNotNull(vehicle.position).distance == 10)
        assertFalse(vehicle.reroute())
        utils.clear()
    }

    @Test
    fun rerouteTestRerouting() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid5.dot"))
        parse.parseMap()
        val vertex0 = requireNotNull(graph.getVertexFromId(0))
        val vertex1 = requireNotNull(graph.getVertexFromId(4))
        val vertex2 = requireNotNull(graph.getVertexFromId(6))
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val road1 = requireNotNull(graph.getRoad(vertex3, vertex2))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 0, 0)
        val emergency = Emergency(0, 1, road, EmergencyType.MEDICAL, 1, 1, 100, res)
        val base = Base(0, 20, vertex0, mutableListOf())
        val vehicle = Vehicle(0, VehicleType.AMBULANCE, base, 4, 1, null)
        base.addVehicle(vehicle)
        emergency.addVehicle(vehicle)
        vehicle.targetEmergency = emergency
        vehicle.position = Dijkstra.dijkstraHeight(vertex0.realid, emergency.road, vehicle.vehicleHeight)
        assertTrue(requireNotNull(vehicle.position).distance == 20)
        vehicle.move()
        assertTrue(requireNotNull(vehicle.position).distance == 10)
        val event = RoadClosureEvent(0, 2, 10, road1)
        event.executeStart()
        assertFalse(vehicle.reroute())
        assertTrue(requireNotNull(vehicle.position).distance == 10)
        utils.clear()
    }

    @Test
    fun rerouteTestGoingBacknoRerouting() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid5.dot"))
        parse.parseMap()
        val vertex0 = requireNotNull(graph.getVertexFromId(0))
        val vertex1 = requireNotNull(graph.getVertexFromId(4))
        val vertex2 = requireNotNull(graph.getVertexFromId(6))
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val road1 = requireNotNull(graph.getRoad(vertex3, vertex2))
        val road2 = requireNotNull(graph.getRoad(vertex0, vertex3))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 0, 0)
        val emergency = Emergency(0, 1, road, EmergencyType.MEDICAL, 1, 1, 100, res)
        val base = Base(0, 20, vertex0, mutableListOf())
        val vehicle = Vehicle(0, VehicleType.AMBULANCE, base, 4, 1, null)
        base.addVehicle(vehicle)
        emergency.addVehicle(vehicle)
        val pos = Position(
            mutableListOf(
                road1,
                road2
            ),
            mutableListOf(vertex2, vertex3, vertex0), 5, 5, vertex3, 15, 2, false, true
        )
        vehicle.position = pos
        assertFalse(vehicle.reroute())
        utils.clear()
    }

    @Test
    fun rerouteTestGoingBackRerouting() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid5.dot"))
        parse.parseMap()
        val vertex0 = requireNotNull(graph.getVertexFromId(0))
        val vertex1 = requireNotNull(graph.getVertexFromId(4))
        val vertex2 = requireNotNull(graph.getVertexFromId(6))
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val road1 = requireNotNull(graph.getRoad(vertex3, vertex2))
        val road2 = requireNotNull(graph.getRoad(vertex0, vertex3))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 0, 0)
        val emergency = Emergency(0, 1, road, EmergencyType.MEDICAL, 1, 1, 100, res)
        val base = Base(0, 20, vertex0, mutableListOf())
        val vehicle = Vehicle(0, VehicleType.AMBULANCE, base, 4, 1, null)
        base.addVehicle(vehicle)
        emergency.addVehicle(vehicle)
        val pos = Position(
            mutableListOf(
                road1,
                road2
            ),
            mutableListOf(vertex2, vertex3, vertex0), 5, 5, vertex3, 15, 2, false, true
        )
        vehicle.position = pos
        val event = RoadClosureEvent(0, 2, 10, road2)
        event.executeStart()
        assertTrue(vehicle.reroute())
        assertTrue(requireNotNull(vehicle.position).distance == 31)
        utils.clear()
    }

    @Test
    fun sendBackToBaseTest() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid5.dot"))
        parse.parseMap()
        val vertex0 = requireNotNull(graph.getVertexFromId(0))
        val vertex1 = requireNotNull(graph.getVertexFromId(4))
        val vertex2 = requireNotNull(graph.getVertexFromId(6))
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val road1 = requireNotNull(graph.getRoad(vertex3, vertex2))
        val road2 = requireNotNull(graph.getRoad(vertex0, vertex3))
        val vehicleList = mutableListOf(VehicleType.AMBULANCE)
        val res = Resource(vehicleList, 0, 0, 0, 0)
        val emergency = Emergency(0, 1, road, EmergencyType.MEDICAL, 1, 1, 100, res)
        val base = Base(0, 20, vertex0, mutableListOf())
        val vehicle = Vehicle(0, VehicleType.AMBULANCE, base, 4, 1, null)
        base.addVehicle(vehicle)
        emergency.addVehicle(vehicle)
        val pos = Position(
            mutableListOf(),
            mutableListOf(vertex0, vertex3, vertex2), 0, 0, vertex2, 0, 0, false, false
        )
        vehicle.position = pos
        vehicle.sendBackToBase()
        assertTrue(requireNotNull(vehicle.position).isDrivingBack)
        assertTrue(requireNotNull(vehicle.position).distance == 20)
        utils.clear()
    }*/
}
