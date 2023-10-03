import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
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
        val vertexList = mutableListOf(v1, v2, v3)
        val vertexList2 = mutableListOf(v1, v2, v4)
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
    }

    @Test
    fun testSmaller2() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val v4 = Vertex(3, null, 3)
        val vertexList = mutableListOf(v1, v2, v3)
        val vertexList2 = mutableListOf(v1, v2, v3, v4)
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
    }

    @Test
    fun testSmaller3() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val v4 = Vertex(3, null, 3)
        val vertexList = mutableListOf(v1, v2, v3, v4)
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
    }

    @Test
    fun isEqual() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val vertexList = mutableListOf(v1, v2, v3)
        val position1 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        val equalPos = position1.isEqual(position1)
        assertTrue(equalPos)
    }

    @Test
    fun isEqual3() {
        val roadList = mutableListOf<Road>()
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val r1 = Road(PrimaryRoadType.COUNTYROAD, SecondaryRoadType.NONE, "Test", "Bahnhof", 20, 5, v1, v2)
        val roadList2 = mutableListOf(r1)
        val vertexList = mutableListOf(v1, v2, v3)
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
            roadList2,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun isEqual4() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val r1 = Road(PrimaryRoadType.COUNTYROAD, SecondaryRoadType.NONE, "Test", "Bahnhof", 20, 5, v1, v2)
        val roadList = mutableListOf(r1)
        val vertexList = mutableListOf(v1, v2, v3)
        val position1 = Position(
            roadList,
            vertexList,
            10,
            10,
            null,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun isEqual6() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val r1 = Road(PrimaryRoadType.COUNTYROAD, SecondaryRoadType.NONE, "Test", "Bahnhof", 20, 5, v1, v2)
        val roadList = mutableListOf(r1)
        val vertexList = mutableListOf(v1, v2, v3)
        val position1 = Position(
            roadList,
            vertexList,
            10,
            0,
            v2,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList,
            0,
            10,
            null,
            0,
            0
        )
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun isEqual5() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val r1 = Road(PrimaryRoadType.COUNTYROAD, SecondaryRoadType.NONE, "Test", "Bahnhof", 20, 5, v1, v2)
        val roadList = mutableListOf(r1)
        val vertexList = mutableListOf(v1, v2, v3)
        val position1 = Position(
            roadList,
            vertexList,
            10,
            10,
            v1,
            0,
            0
        )
        val position2 = Position(
            roadList,
            vertexList,
            10,
            10,
            v1,
            10,
            0
        )
        assertFalse(position1.isEqual(position2))
        assertFalse(position2.isEqual(position1))
    }

    @Test
    fun advance1() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val v3 = Vertex(2, null, 2)
        val vertexList = mutableListOf(v1, v2, v3)

        val r1 = Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Test", "Bahnhofstrasse", 20, 5, v1, v2)
        val r2 = Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Test", "Bahnhof", 20, 5, v2, v3)
        val roadList1 = mutableListOf(r1, r2)
        val roadList2 = mutableListOf(r1, r2)

        val pos1 = Position(roadList1, vertexList, 0, 10, v2, 20, 2, false, false)
        val pos2 = Position(roadList2, vertexList, 0, 10, v2, 20, 2, false, false)
        pos2.advance()
        assertFalse(pos1.isEqual(pos2))
        assertFalse(pos2.isEqual(pos1))
    }

    @Test
    fun advance2() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        val vertList = mutableListOf(v1, v2)
        val r = Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Test", "SimpleStreet", 10, 5, v1, v2)
        val roadList1 = mutableListOf(r)
        val roadList2 = mutableListOf(r)
        val pos1 = Position(roadList1, vertList, 0, 10, v2, 10, 1, false, false)
        val pos2 = Position(roadList2, vertList, 3, 7, v2, 7, 1, false, false)
        pos1.advance()
        pos2.advance()
        assertTrue(pos1.isEqual(pos2))
    }

    /*@Test
    fun advance3() {
        val v0 = Vertex(0, null, 0)
        val v1 = Vertex(1, null, 1)
        val v2 = Vertex(2, null, 2)
        val vertList = mutableListOf(v0, v1, v2)
        val r1 = Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Test", "SimpleStreet", 10, 5, v0, v1)
        val r2 = Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Test", "SimplerStreet", 10, 5, v1, v2)
        val rL1 = mutableListOf(r1, r2)
        val rL2 = mutableListOf(r1, r2)
        val rL3 = mutableListOf(r1, r2)
        val pos1 = Position(rL1, vertList, 3, 7, v1, 17, 2, false, false)
        val pos2 = Position(rL2, vertList, 2, 8, v1, 18, 2, false, false)
        val pos3 = Position(rL3, vertList, 2, 8, v2, 8, 1, true, false)
        assertFalse(pos1.isEqual(pos2))
        assertTrue(pos2.isEqual(pos3))
    }*/
}
