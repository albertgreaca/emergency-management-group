import de.unisaarland.cs.se.selab.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class GraphMapTest {

    @Test
    fun checkRoadAdding() {
        val gm = GraphMap()
        val parser = MapParser(gm, File("src/test/resources/mapvalid1.dot"))
        parser.parseMap()
        val roadList = gm.roadList
        val vert0 = Vertex(0, null)
        val vert1 = Vertex(1, null)
        val vert2 = Vertex(2, null)
        val r1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Teststrasse", 10, 5, vert1, vert2)
        assertTrue(roadList.contains(r1))
        val r2 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Saarbrücken", "Campus", 10, 1, vert0, vert1)
        assertTrue(roadList.contains(r2))
        val adjList: MutableList<MutableMap<Vertex,Road>> = gm.adjacencyList
        val map0 = adjList[0]
        assertTrue(map0.containsKey(vert1)) //check existence of connection between vert0 and vert1
        val supposed2 = map0[vert1]
            assertTrue(supposed2 == r2) //check if the connection between vert0 and vert1 is supposed one
        val map1 = adjList[1]
        assertTrue(map1.containsKey(vert0)) // check if the said connection is bidirectional
        val supposed2Again = map1[vert0]
        assertTrue(supposed2Again == r2) //check for the other way around
        assertTrue(map1.containsKey(vert2)) //check if vert1 is connected to vert2
        val supposed1 = map1[vert2]
        assertTrue(supposed1 == r1) //check if the vert1 to vert2 connection is the correct one
        //now check for bidirectionality
        val map2 = adjList[2]
        assertTrue(r1 == map2[vert2])
    }
}