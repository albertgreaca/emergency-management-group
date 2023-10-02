
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFalse

class JsonParserConfig3Semantics {

    @Test
    fun invalidTestSemantics1() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics2() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid2.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics3() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid3.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics4() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid4.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics5() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid5.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics6() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid6.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics7() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid7.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics8() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid8.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics9() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid9.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics10() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid10.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics11() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid11.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics12() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid12.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics13() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/mapForJSONConfig3Test.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/validConfig2/config2valid1.json"),
                File("src/test/resources/invalidConfig2/Sematics/config3invalid13.json")
            )
        assertFalse(parser.parseBases())
    }
}
