
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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

        val ambulanceDep = EMCC.ambulanceDepartment
        val em = Simulation.emergencies[0]
        ambulanceDep?.update(em)

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

        val ambulanceDep = EMCC.ambulanceDepartment
        val em = Simulation.emergencies[0]
        ambulanceDep?.update(em)

        assertEquals(1, em.base?.id)
    }


}
