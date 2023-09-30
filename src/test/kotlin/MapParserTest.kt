import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class MapParserTest {

    /**
     Testing for Id 0 Valid
     */
    @Test
    fun validDotFile1() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid1.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.vertexList.size == 3)
        assertTrue(graphMap.roadList.size == 2)
    }

    /**
     * Testing for Country Road belonging to different city but connecting to other city vertex
     */
    @Test
    public fun validDotFile2() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid2.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.vertexList.size == 7)
        assertTrue(graphMap.roadList.size == 6)
    }

    @Test
    public fun validDotFile3() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid3.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.vertexList.size == 8)
        assertTrue(graphMap.roadList.size == 9)
    }

    @Test
    fun validExampleFile() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/example_map.dot"))
        assertTrue(parser.parseMap())
    }
}
