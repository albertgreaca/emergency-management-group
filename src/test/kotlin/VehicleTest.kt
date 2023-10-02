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

class VehicleTest {

    @Test
    fun testMove1() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex1, vertex2)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 5, 4, vertex2, vertex3)

        val roadlist = mutableListOf(road1, road2)
        val vertexlist = mutableListOf(vertex1, vertex2, vertex3)
        val expected = mutableListOf(road2)

        val pos = Position(roadlist, vertexlist, 10, 10, vertex2, 15, 2, startedThisTick = false, isDrivingBack = false)

        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)

        vehicle.move()

        assertTrue(roadlist.size == 1)
        assertTrue(pos.distanceFromEnd == 5)
        assertTrue(pos.distanceFromStart == 0)
        assertTrue(pos.distance == 5)
        assertTrue(pos.arrivalTicks == 1)
        assertTrue(pos.startedThisTick == false)
        assertTrue(pos.isDrivingBack == false)
        assertTrue(pos.destinationVertex == vertex3)
        assertTrue(pos.roadList == expected)
    }

    @Test
    fun testMove2StartedThisTick() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 5, 4, vertex1, vertex2)

        val roadlist = mutableListOf(road1, road2)
        val vertexlist = mutableListOf(vertex0, vertex1, vertex2)

        val pos = Position(roadlist, vertexlist, 0, 20, vertex3, 25, 3, startedThisTick = true, isDrivingBack = false)

        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        vehicle.move()

        assertTrue(pos.startedThisTick == false)
        assertTrue(pos.isDrivingBack == false)
        assertTrue(roadlist.size == 2)
        assertTrue(pos.distanceFromEnd == 20)
        assertTrue(pos.distanceFromStart == 0)
        assertTrue(pos.distance == 25)
        assertTrue(pos.arrivalTicks == 3)
        assertTrue(pos.destinationVertex == vertex3)
    }

    @Test
    fun testReroutablePositionNull() {
        val pos: Position? = null
        val vertex0 = Vertex(0, null, 0)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)

        assertFalse(vehicle.reroutable())
    }

    @Test
    fun testReroutableArrivalTicks0() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val roadlist = mutableListOf(road1)
        val vertexlist = mutableListOf(vertex0, vertex1)

        val pos = Position(roadlist, vertexlist, 20, 0, vertex1, 0, 0, startedThisTick = false, isDrivingBack = false)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        assertFalse(vehicle.reroutable())
    }

    @Test
    fun testReroutableTrue() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val roadlist = mutableListOf(road1)
        val vertexlist = mutableListOf(vertex0, vertex1)

        val pos = Position(roadlist, vertexlist, 0, 20, vertex1, 20, 2, startedThisTick = false, isDrivingBack = false)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)

        assertTrue(vehicle.reroutable())
    }

    @Test
    fun testReallocatablePositionNull() {
        val pos: Position? = null

        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        val re = Resource(mutableListOf(VehicleType.POLICE_CAR), 0, 0, 0, 0)
        val emergency = Emergency(50, 1, road1, EmergencyType.CRIME, 2, 2, 3, re)
        assertFalse(vehicle.reallocatable(emergency))
    }

    @Test
    fun testReallocatableArrivalTicks0() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val roadlist = mutableListOf(road1)
        val vertexlist = mutableListOf(vertex0, vertex1)

        val pos = Position(roadlist, vertexlist, 20, 0, vertex1, 0, 0, startedThisTick = false, isDrivingBack = false)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        val re = Resource(mutableListOf(VehicleType.POLICE_CAR), 0, 0, 0, 0)
        val emergency = Emergency(50, 1, road1, EmergencyType.CRIME, 2, 2, 3, re)
        val emergency2 = Emergency(51, 1, road1, EmergencyType.MEDICAL, 3, 3, 4, re)
        vehicle.targetEmergency = emergency2
        assertFalse(vehicle.reallocatable(emergency))
    }

    @Test
    fun testReallocatableSeverity() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val roadlist = mutableListOf(road1)
        val vertexlist = mutableListOf(vertex0, vertex1)

        val pos = Position(roadlist, vertexlist, 20, 0, vertex1, 0, 3, startedThisTick = false, isDrivingBack = false)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        val re = Resource(mutableListOf(VehicleType.POLICE_CAR), 0, 0, 0, 0)
        val emergency = Emergency(50, 1, road1, EmergencyType.CRIME, 2, 2, 3, re)
        val emergency2 = Emergency(51, 1, road1, EmergencyType.MEDICAL, 3, 3, 4, re)
        vehicle.targetEmergency = emergency2
        assertFalse(vehicle.reallocatable(emergency))
    }

    @Test
    fun testReallocatableBaseWaitingTicks() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val roadlist = mutableListOf(road1)
        val vertexlist = mutableListOf(vertex0, vertex1)

        val pos = Position(roadlist, vertexlist, 20, 0, vertex1, 0, 3, startedThisTick = false, isDrivingBack = false)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        vertex0.base = base
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        val re = Resource(mutableListOf(VehicleType.POLICE_CAR), 0, 0, 0, 0)
        val emergency = Emergency(50, 1, road1, EmergencyType.CRIME, 3, 2, 3, re)
        val emergency2 = Emergency(51, 1, road1, EmergencyType.MEDICAL, 2, 3, 4, re)
        vehicle.targetEmergency = emergency2
        vehicle.baseWaitingTicks = 2
        assertFalse(vehicle.reallocatable(emergency))
    }
}
