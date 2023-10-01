import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFalse

class JsonParserConfig2SemanticsTest {
    @Test
    fun invalidTestSemantics1() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid1.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics2() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid2.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics3() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid3.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics4() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid4.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics5() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid5.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics6() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid6.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics7() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid7.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics8() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid8.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics9() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid9.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics10() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid10.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertTrue(parser.parseBases())
        assertFalse(parser.parseVehicles())
    }

    @Test
    fun invalidTestSemantics11() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid11.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics12() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid12.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics13() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid13.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics14() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid14.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertTrue(parser.parseBases())
        assertFalse(parser.parseVehicles())
    }

    @Test
    fun invalidTestSemantics15() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid15.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertTrue(parser.parseBases())
        assertFalse(parser.parseVehicles())
    }

    @Test
    fun invalidTestSemantics16() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid16.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics17() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid17.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics18() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid18.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertTrue(parser.parseBases())
        assertFalse(parser.parseVehicles())
    }

    @Test
    fun invalidTestSemantics20() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid20.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics21() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid21.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertTrue(parser.parseBases())
        assertFalse(parser.parseVehicles())
    }

    @Test
    fun invalidTestSemantics22() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid22.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics23() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid23.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics24() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid24.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics25() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid25.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics26() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid26.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics27() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid27.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics28() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid28.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics29() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid29.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics30() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid30.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics31() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid31.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics32() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid32.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics33() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid33.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics34() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid34.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics35() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid35.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics36() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid36.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics37() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid37.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics38() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid38.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics39() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid39.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics40() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid40.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics41() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid41.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics42() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid42.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics43() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid43.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics44() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid44.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics45() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid45.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }

    @Test
    fun invalidTestSemantics46() {
        val map = GraphMap()
        val mapparser = MapParser(map, File("src/test/resources/example_map.dot"))
        mapparser.parseMap()
        val parser =
            JsonParser(
                map,
                File("src/test/resources/invalidConfig2/Semantics/config2invalid46.json"),
                File("src/test/resources/validConfig3/config3valid1.json")
            )
        assertFalse(parser.parseBases())
    }
}
