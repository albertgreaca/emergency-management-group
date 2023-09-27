import de.unisaarland.cs.se.selab.GraphMap
import de.unisaarland.cs.se.selab.JsonParser
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class JsonParserConfig2Test {
    @Test
    fun testParseBases() {
        val map = GraphMap()
        val parser = JsonParser(
            map,
            File("src/test/resources/invalidConfig2/Semantics/config2Invalid1.json"),
            File("src/test/resources/config3valid1.json")
        )
        assertTrue(!parser.parseBases())
    }
}
