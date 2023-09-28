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

    @Test
    public fun invalidSyntaxDotFile1() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid1.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile2() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid2.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile3() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid3.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile4() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid4.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile5() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid5.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile6() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid6.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile7() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid7.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile8() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid8.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile9() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid9.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile10() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid10.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile11() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid11.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile12() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid12.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile13() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid13.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile14() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid14.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile15() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid15.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile16() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid16.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile17() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid17.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile18() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid18.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile19() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid19.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile20() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid20.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile21() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid21.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile22() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid22.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile23() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid23.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile24() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid24.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile25() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid25.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile26() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid26.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile27() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid27.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile28() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid28.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile29() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid29.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile30() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid30.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile31() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid31.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile32() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid32.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile33() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid33.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile34() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid34.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile35() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid35.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile36() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid36.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile37() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid37.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile38() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid38.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile39() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid39.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile40() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid40.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile41() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid41.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile42() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid42.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile43() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid43.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile44() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid44.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile45() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid45.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile46() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid46.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile47() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid47.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile48() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid48.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile49() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid49.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile50() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid50.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile51() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid51.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile52() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid52.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile53() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid53.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile54() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid54.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile55() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid55.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile56() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid56.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile57() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid57.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile58() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid58.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile59() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid59.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile60() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid60.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile61() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid61.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile62() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid62.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile63() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid63.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile64() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid64.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile65() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid65.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile66() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid66.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile67() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid67.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile68() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid68.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile69() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid69.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile70() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid70.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile71() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid71.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile72() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid72.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile73() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid73.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile74() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid74.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    public fun invalidSyntaxDotFile75() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid75.dot"))
        assertFalse(parser.parseMap())
    }

    @Test
    fun invalidSyntaxDotFile76() {
        val graphMap = GraphMap()
        val parser = MapParser(graphMap, File("src/test/resources/invalidMaps/SyntaxIssues/mapinvalid76.dot"))
        assertFalse(parser.parseMap())
    }

    // compare sizes of parsed elements with expected values
    // check individual field values of parsed objects
}
