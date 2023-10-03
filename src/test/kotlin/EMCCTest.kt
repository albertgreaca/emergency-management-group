
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EMCCTest {

    var utils: TestUtils = TestUtils()

    @BeforeEach
    fun beforeEach() {
        utils.clear()
    }

    @Test
    fun testOrderEmergencies() {
        val parse = MapParser(Simulation.map, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            Simulation.map,
            File("src/test/resources/UnitTestConfig2/basesSimple.json"),
            File("src/test/resources/UnitTestConfig3/manyEmergencies.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        for (em in Simulation.emergencies) {
            EMCC.addStartingEmergency(em)
        }
        assertTrue(EMCC.startingEmergencies.size == 7)

        val ems = Simulation.emergencies
        val testEMs = mutableListOf(ems[0], ems[4], ems[6], ems[1], ems[3], ems[2], ems[5])

        EMCC.orderEmergencies()

        assertTrue(EMCC.startingEmergencies.size == 7)

        assertEquals(testEMs, EMCC.startingEmergencies)
    }
}
