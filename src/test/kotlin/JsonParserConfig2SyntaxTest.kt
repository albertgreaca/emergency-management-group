import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFalse

class JsonParserConfig2SyntaxTest {

    @Test
    fun invalidTestSyntax1() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid1.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    /* @Test                    cant work because exception is caught in Simulation
    fun invalidTestSyntax2() {
        val map = GraphMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid2.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    } */

    @Test
    fun invalidTestSyntax3() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid3.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax4() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid4.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax5() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid5.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax6() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid6.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax7() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid7.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax8() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid8.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax9() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid9.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax10() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid10.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax11() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid11.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax12() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid12.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax13() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid13.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax14() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid14.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax15() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid15.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax16() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid16.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax17() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid17.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax18() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid18.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax19() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid19.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax20() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid20.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax21() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid21.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax22() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid22.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax23() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid23.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax24() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid24.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax25() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid25.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax26() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid26.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax27() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid27.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax28() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid28.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax29() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid29.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    /* @Test                    cant work because exception is caught in Simulation
    fun invalidTestSyntax30() {
        val map = GraphMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid30.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    } */

    @Test
    fun invalidTestSyntax31() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Syntax/config2invalid31.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics48() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapforJSONTests.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid48.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics49() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid49.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics50() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid50.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics47() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid47.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }
}
