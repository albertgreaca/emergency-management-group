import de.unisaarland.cs.se.selab.Lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class LexerTest {

    @Test
    fun simpletest() {
        val lex = Lexer()
        val string = lex.lex(
            "Test {\n" +
                    "0;\n" +
                    "1;\n" +
                    "2;\n" +
                    "0->1[village ="
        )
        assertTrue(false, string.toString())
    }
}