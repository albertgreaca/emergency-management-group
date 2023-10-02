import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.graphlogic.Dijkstra
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RerouteTest {

    @Test
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
    }
/**
     @Test
     fun rerouteTestRerouting() {
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
     val event = RoadClosureEvent(0, 1, 10, )

     assertFalse(vehicle.reroute())
     }
     */
}
