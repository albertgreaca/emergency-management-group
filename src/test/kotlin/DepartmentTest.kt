
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class DepartmentTest {

    @Test
    fun testFireDepFindBaseFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fireDep = EMCC.fireDepartment

        assertEquals(fireDep?.findBase(0)?.id, 0)
    }

    @Test
    fun testFireDepFindBaseNotFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fireDep = EMCC.fireDepartment

        assertNull(fireDep?.findBase(1)?.id)
    }

    @Test
    fun testPoliceDepFindBaseFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val policeDep = EMCC.policeDepartment

        assertEquals(policeDep?.findBase(0)?.id, 0)
    }

    @Test
    fun testPoliceDepFindBaseNotFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val policeDep = EMCC.policeDepartment

        assertNull(policeDep?.findBase(4)?.id)
    }

    @Test
    fun testAmbulanceDepFindBaseFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val ambulanceDep = EMCC.ambulanceDepartment

        assertEquals(ambulanceDep?.findBase(0)?.id, 0)
    }

    @Test
    fun testAmbulanceDepFindBaseNotFound() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val ambulanceDep = EMCC.ambulanceDepartment

        assertNull(ambulanceDep?.findBase(2)?.id)
    }

    @Test
    fun testAmbulanceDepUpdate() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/medicalEmergency1TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val ambulanceDep = EMCC.ambulanceDepartment
        val em = Simulation.emergencies[0]
        ambulanceDep?.update(em)

        assertEquals(2, em.base?.id)
    }

    @Test
    fun testFireDepUpdate() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/fireEmergency2TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fireDep = EMCC.fireDepartment
        val em = Simulation.emergencies[0]
        fireDep?.update(em)

        assertEquals(0, em.base?.id)
    }

    @Test
    fun testPoliceDepUpdate() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/crimeEmergency3TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val policeDep = EMCC.policeDepartment
        val em = Simulation.emergencies[0]
        policeDep?.update(em)

        assertEquals(1, em.base?.id)
    }

    @Test
    fun testAmbulanceDepUpdateVehicles() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/crimeEmergency3TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val vehicles = requireNotNull(EMCC.ambulanceDepartment).bases[0].vehicles
        assertEquals(3, vehicles.size)

        vehicles[0].baseWaitingTicks = 1
        vehicles[0].available = false
        vehicles[1].baseWaitingTicks = 5
        vehicles[1].available = false
        vehicles[2].baseWaitingTicks = 0
        vehicles[2].available = true

        requireNotNull(EMCC.ambulanceDepartment).updateVehicles()

        assertEquals(0, vehicles[0].baseWaitingTicks)
        assertTrue(vehicles[0].available)
        assertEquals(4, vehicles[1].baseWaitingTicks)
        assertFalse(vehicles[1].available)
        assertEquals(0, vehicles[2].baseWaitingTicks)
        assertTrue(vehicles[2].available)
    }

    @Test
    fun testFireDepUpdateVehicles() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/crimeEmergency3TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val vehicles = requireNotNull(EMCC.fireDepartment).bases[0].vehicles
        assertEquals(4, vehicles.size)

        vehicles[0].baseWaitingTicks = 1
        vehicles[0].available = false
        vehicles[1].baseWaitingTicks = 2
        vehicles[1].available = false
        vehicles[2].baseWaitingTicks = 0
        vehicles[2].available = true
        vehicles[3].baseWaitingTicks = 7
        vehicles[3].available = false

        requireNotNull(EMCC.fireDepartment).updateVehicles()

        assertEquals(0, vehicles[0].baseWaitingTicks)
        assertTrue(vehicles[0].available)
        assertEquals(1, vehicles[1].baseWaitingTicks)
        assertFalse(vehicles[1].available)
        assertEquals(0, vehicles[2].baseWaitingTicks)
        assertTrue(vehicles[2].available)
        assertEquals(6, vehicles[3].baseWaitingTicks)
        assertFalse(vehicles[3].available)
    }

    @Test
    fun testPoliceDepUpdateVehicles() {
        val graph = GraphMap()
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig2/crimeEmergency3TrafficJam.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val vehicles = requireNotNull(EMCC.policeDepartment).bases[0].vehicles
        assertEquals(3, vehicles.size)

        vehicles[0].baseWaitingTicks = 1
        vehicles[0].available = false
        vehicles[1].baseWaitingTicks = 0
        vehicles[1].available = true
        vehicles[2].baseWaitingTicks = 4
        vehicles[2].available = false

        requireNotNull(EMCC.policeDepartment).updateVehicles()

        assertEquals(0, vehicles[0].baseWaitingTicks)
        assertTrue(vehicles[0].available)
        assertEquals(0, vehicles[1].baseWaitingTicks)
        assertTrue(vehicles[1].available)
        assertEquals(3, vehicles[2].baseWaitingTicks)
        assertFalse(vehicles[2].available)
    }
}
