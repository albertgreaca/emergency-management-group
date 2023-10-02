import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse

class JsonParserConfig3SyntaxTest {

    @Test
    fun invalidTestSyntax1() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax2() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid2.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax3() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid3.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax4() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid4.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax5() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid5.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax6() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid6.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax7() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid7.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax8() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid8.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax9() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid9.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax10() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid10.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax11() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid11.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax12() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid12.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax13() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid13.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax14() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid14.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax15() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid15.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax16() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid16.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax17() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid17.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax18() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid18.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax19() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid19.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax20() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid20.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax21() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid21.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax22() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid22.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax23() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid23.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax24() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid24.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSyntax25() {
        val map = GraphMap()
        val mapParser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapParser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig3/Syntax/config3invalid25.json")
            )
        assertFalse(parser.parseBases())
    }
}
