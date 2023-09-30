import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.utils.Position
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PositionTest {
    @Test
    fun testSmaller() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val v4 = Vertex(3, null, 3)
        val vertexList = mutableListOf<Vertex>(v1, v2, v3)
        val vertexList2 = mutableListOf<Vertex>(v1, v2, v4)
        val position1 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList2,
            0,
            10,
            null,
            0,
            0
        )
        assertTrue(position1.smaller(position2))
        assertFalse(position2.smaller(position1))
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun testSmaller2() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val v4 = Vertex(3, null, 3)
        val vertexList = mutableListOf<Vertex>(v1, v2, v3)
        val vertexList2 = mutableListOf<Vertex>(v1, v2, v3, v1)
        val position1 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList2,
            0,
            10,
            null,
            0,
            0
        )
        assertTrue(position1.smaller(position2))
        assertFalse(position2.smaller(position1))
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun testSmaller3() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val v4 = Vertex(3, null, 3)
        val vertexList = mutableListOf<Vertex>(v1, v2, v3)
        val vertexList2 = mutableListOf<Vertex>()
        val position1 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList2,
            0,
            10,
            null,
            0,
            0
        )
        assertTrue(position2.smaller(position1))
        assertFalse(position1.smaller(position2))
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun isEqual() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val vertexList = mutableListOf<Vertex>(v1, v2, v3)
        val position1 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        assertTrue(position1.equals(position1))
    }
}
