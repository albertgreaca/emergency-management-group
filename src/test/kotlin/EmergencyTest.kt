import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.utils.Position
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmergencyTest {
    @Test
    fun testAdd() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val re = Resource(mutableListOf(), 5, 5, 8, null)

        val em = Emergency(0, 1, road, EmergencyType.CRIME, 2, 5, 10, re)

        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val pos: Position? = null
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)

        em.addVehicle(vehicle)
        assertTrue(em.assignedVehicles.contains(vehicle))
    }

    @Test
    fun testRemove() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val re = Resource(mutableListOf(), 5, 5, 8, null)

        val em = Emergency(0, 1, road, EmergencyType.CRIME, 2, 5, 10, re)

        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val pos: Position? = null
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        em.assignedVehicles.add(vehicle)

        em.removeVehicle(vehicle)
        assertFalse(em.assignedVehicles.contains(vehicle))
        assertTrue(re.vehicles.contains(VehicleType.AMBULANCE))
    }
}
