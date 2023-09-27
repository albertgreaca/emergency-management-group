
import de.unisaarland.cs.se.selab.Base
import de.unisaarland.cs.se.selab.Position
import de.unisaarland.cs.se.selab.PrimaryRoadType
import de.unisaarland.cs.se.selab.Road
import de.unisaarland.cs.se.selab.SecondaryRoadType
import de.unisaarland.cs.se.selab.Vehicle
import de.unisaarland.cs.se.selab.VehicleType
import de.unisaarland.cs.se.selab.Vertex
import org.junit.jupiter.api.Test

class VehicleTest {
    @Test
    fun testMove() {
        val vertex0 = Vertex(0, null)
        val vertex1 = Vertex(1, null)
        val vertex2 = Vertex(2, null)
        val vertex3 = Vertex(3, null)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex1, vertex2)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 5, 4, vertex2, vertex3)

        val roadlist = mutableListOf(road1, road2)
        val vertexlist = mutableListOf(vertex1, vertex2)
        val expected = mutableListOf(road2)

        var pos = Position(roadlist, vertexlist, 10, vertex3, 15, 2, false)

        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        var vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)

        vehicle.move()

        assert(roadlist.size == 1)
        assert(pos.positionOnRoad == 0)
        assert(pos.distance == 5)
        assert(pos.arrivalTicks == 1)
        assert(pos.roadList.equals(expected))
        // wenn started this tick true nicht bewegen
    }
}
