import de.unisaarland.cs.se.selab.GraphMap
import de.unisaarland.cs.se.selab.MapParser
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class MapParserTest {

    /**
     Testing for Id 0 Valid
     */
    @Test
    public fun validDotFile1() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid1.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.getVertexList().size == 2)
        assertTrue(graphMap.getRoadList().size == 2)
    }

    /**
     * Testing for Country Road belonging to different city but connecting to other city vertex
     */
    @Test
    public fun validDotFile2() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid2.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.getVertexList().size == 7)
        assertTrue(graphMap.getRoadList().size == 6)
    }

    @Test
    public fun validDotFile3() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/mapvalid3.dot"))
        assertTrue(parser.parseMap())
        assertTrue(graphMap.getVertexList().size == 7)
        assertTrue(graphMap.getRoadList().size == 6)
    }

    /**
     Testing for unique IDs
     */
    @Test
    public fun invalidDotFile1() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid1.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidDotFile2() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid2.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks for Unique Roadname
     */
    @Test
    public fun invalidDotFile3() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid3.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * One vertex is not connected to another
     */
    @Test
    public fun invalidDotFile4() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid4.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Tests for Edge from Vertex to itself
     */
    @Test
    public fun invalidDotFile5() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid5.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Tests for two edges between same vertices
     * */
    @Test
    public fun invalidDotFile6() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid6.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks for Edge connecting with non existing Vertex
     */
    @Test
    public fun invalidDotFile7() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid7.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if Edges are connected to same Vertex belong to same city
     */
    @Test
    public fun invalidDotFile8() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid8.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if a village does not have a mainStreet
     */
    @Test
    public fun invalidDotFile9() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid9.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if sideStreet Constraint is met
     */
    @Test
    public fun invalidDotFile10() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid10.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if weight < 0 Constraint is met
     */
    @Test
    public fun invalidDotFile11() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid11.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if weight = 0 Constraint is met
     */
    @Test
    public fun invalidDotFile12() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid12.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if height < 0 Constraint is met
     */
    @Test
    public fun invalidDotFile13() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid13.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if height < 0 Constraint is met
     */
    @Test
    public fun invalidDotFile14() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid14.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if height tunnel <=3 Constraint is met
     */
    @Test
    public fun invalidDotFile15() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid15.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if height tunnel <=1 Constraint is met
     */
    @Test
    public fun invalidDotFile16() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid16.dot"))
        assertFalse(parser.parseMap())
    }

    /**
     * Checks if Name of County is equal to a village name
     */
    @Test
    public fun invalidDotFile17() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SemanticIssues/mapinvalid17.dot"))
        assertFalse(parser.parseMap())
    }
    // compare sizes of parsed elements with expected values
    // check individual field values of parsed objects
}
