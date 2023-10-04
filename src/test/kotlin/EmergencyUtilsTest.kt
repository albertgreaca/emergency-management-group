import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.emergencies.EmergencyUtils
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.Ambulance
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class EmergencyUtilsTest {
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
            val result = emUtil.potentialPatients(em)
            assertTrue(result == 3)
        }
    }
}

