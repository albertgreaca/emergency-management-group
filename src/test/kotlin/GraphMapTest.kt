import de.unisaarland.cs.se.selab.GraphMap
import de.unisaarland.cs.se.selab.MapParser
import de.unisaarland.cs.se.selab.PrimaryRoadType
import de.unisaarland.cs.se.selab.Road
import de.unisaarland.cs.se.selab.SecondaryRoadType
import de.unisaarland.cs.se.selab.Vertex
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class GraphMapTest {

    @Test
    fun checkRoadAddingMV1() {
        val gm = GraphMap()
        val parser = MapParser(gm, File("src/test/resources/mapvalid1.dot"))
        parser.parseMap()
        val roadList = gm.roadList
        val vert0 = Vertex(0, null, 0)
        val vert1 = Vertex(1, null, 1)
        val vert2 = Vertex(2, null, 2)
        val r1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Teststrasse", 10, 5, vert1, vert2)
        assertTrue(roadList.contains(r1))
        val r2 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Saarbruecken", "Campus", 10, 1, vert0, vert1)
        assertTrue(roadList.contains(r2))
        val adjList: MutableList<MutableMap<Vertex, Road>> = gm.adjacencyList
        val map0 = adjList[0]
        assertTrue(map0.containsKey(vert1)) // check existence of connection between vert0 and vert1
        val supposed2 = map0[vert1]
        assertTrue(supposed2 == r2) // check if the connection between vert0 and vert1 is supposed one
        val map1 = adjList[1]
        assertTrue(map1.containsKey(vert0)) // check if the said connection is bidirectional
        val supposed2Again = map1[vert0]
        assertTrue(supposed2Again == r2) // check for the other way around
        assertTrue(map1.containsKey(vert2)) // check if vert1 is connected to vert2
        val supposed1 = map1[vert2]
        assertTrue(supposed1 == r1) // check if the vert1 to vert2 connection is the correct one
        // now check for bidirectionality
        val map2 = adjList[2]
        assertTrue(r1 == map2[vert1])
    }

    @Test
    fun checkRoadAddingMV2() {
        val gm = GraphMap()
        val parser = MapParser(gm, File("src/test/resources/mapvalid2.dot"))
        parser.parseMap()
        val rl = gm.roadList
        val vert0 = Vertex(0, null, 0)
        val vert1 = Vertex(1, null, 1)
        val vert2 = Vertex(2, null, 2)
        val vert3 = Vertex(3, null, 3)
        val vert4 = Vertex(4, null, 4)
        val vert5 = Vertex(5, null, 5)
        val vert6 = Vertex(6, null, 6)
        val r01 =
            Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Saarbruecken", "Campus", 10, 5, vert0, vert1)
        val r12 =
            Road(PrimaryRoadType.COUNTYROAD, SecondaryRoadType.NONE, "Test", "Teststrasse", 10, 5, vert1, vert2)
        val r13 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Rathaus", 13, 7, vert1, vert3)
        val r24 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarlouis", "Hauptstrasse", 12, 4, vert2, vert4)
        val r45 = Road(
            PrimaryRoadType.SIDESTREET,
            SecondaryRoadType.NONE,
            "Saarlouis",
            "Saarbrueckerstrasse",
            12,
            4, vert4, vert5
        )
        val r56 = Road(
            PrimaryRoadType.MAINSTREET,
            SecondaryRoadType.NONE,
            "Saarlouis",
            "Groebenhueterstrasse",
            12,
            4,
            vert5,
            vert6
        )
        assertTrue(
            rl.contains(r01) && rl.contains(r12) && rl.contains(r13)
        )
        assertTrue(
            gm.roadList.contains(r24) && gm.roadList.contains(
                r45
            ) && gm.roadList.contains(r56)
        )
        val adjList: MutableList<MutableMap<Vertex, Road>> = gm.adjacencyList
        val map0 = adjList[0]
        assertTrue(map0[vert1] == r01)
        val map1 = adjList[1]
        assertTrue(map1[vert0] == r01 )//&& map1[vert3] == r13 && map1[vert2] == r12)
        val map2 = adjList[2]
        val supposedRoad = map2[vert1]
        assertTrue(supposedRoad == r12 && map2[vert4] == r24)
        val map3 = adjList[3]
        assertTrue(map3[vert1] == r13)
        val map5 = adjList[5]
        assertTrue(map5[vert6] == r56 && map5[vert4] == r45)
    }

    @Test
    fun checkRoadAddingMV3() {
        val gm = GraphMap()
        val parser = MapParser(gm, File("src/test/resources/mapvalid3.dot"))
        parser.parseMap()
        val roadList = gm.roadList
        val vert0 = Vertex(0, null, 0)
        val vert4 = Vertex(4, null, 4)
        val r1 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Uni", "Business_area", 10, 5, vert0, vert4)
        assertTrue(roadList.contains(r1))
        val adjList = gm.adjacencyList
        val map0 = adjList[0]
        val map4 = adjList[4]
        assertTrue(map0.containsKey(vert4) && map4.containsKey(vert0))
    }
}
