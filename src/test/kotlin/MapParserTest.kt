import de.unisaarland.cs.se.selab.GraphMap
import de.unisaarland.cs.se.selab.MapParser
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class MapParserTest {

    @Test
    public fun validDotFile() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid1.dot"))
        assertTrue(parser.parseMap())
    }

    @Test
    public fun invalidDotFile1() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapinvalid1.dot"))
        assertFalse(parser.parseMap())
    }

    // compare sizes of parsed elements with expected values
    // check individual field values of parsed objects
}
