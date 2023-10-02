import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.events.ConstructionSiteEvent
import de.unisaarland.cs.se.selab.events.RoadClosureEvent
import de.unisaarland.cs.se.selab.events.RushHourEvent
import de.unisaarland.cs.se.selab.events.TrafficJamEvent
import de.unisaarland.cs.se.selab.events.VehicleUnavailableEvent
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.utils.Position
import de.unisaarland.cs.se.selab.vehicles.Vehicle
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EventsTest {

    val utils = TestUtils()

    @Test
    fun testExecuteStartEverythingEmpty() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 20, 4, vertex2, vertex3)

        val roadlist = mutableListOf(road1, road2)
        val event = RushHourEvent(0, 1, 2, roadlist, 2)

        assertTrue(event.executeStart())
        assertTrue(road1.eventList.contains(event))
        assertTrue(road2.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteStartOneNotEmpty() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event0 = ConstructionSiteEvent(0, 1, 3, false, road1, 3, 2, 3)
        road1.addEvent(event0)

        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 20, 4, vertex2, vertex3)

        val roadlist = mutableListOf(road1, road2)
        val event = RushHourEvent(1, 2, 2, roadlist, 2)
        val expected = mutableListOf(event0, event)

        assertTrue(event.executeStart())
        assertEquals(road1.eventList, expected)
        assertTrue(road1.eventList.contains(event))
        assertTrue(road2.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteStartNothingEmpty() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event0 = ConstructionSiteEvent(0, 1, 3, false, road1, 3, 2, 3)
        road1.addEvent(event0)

        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 20, 4, vertex2, vertex3)
        road2.addEvent(event0)
        val roadlist = mutableListOf(road1, road2)
        val event = RushHourEvent(1, 2, 2, roadlist, 2)
        val expected = mutableListOf(event0)

        assertFalse(event.executeStart())
        assertTrue(road1.eventList == expected)
        assertTrue(road2.eventList == expected)
        assertTrue(event.tick == 3)
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testStop() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)

        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 20, 4, vertex2, vertex3)

        val roadlist = mutableListOf(road1, road2)
        val event = RushHourEvent(1, 2, 2, roadlist, 2)
        road1.addEvent(event)
        road2.addEvent(event)

        event.stopEvent()
        assertFalse(road1.eventList.contains(event))
        assertFalse(road2.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    /*@Test
    fun testExecuteStartConsSiteTrue() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)
        assertTrue(event.executeStart())
        assertTrue(road.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }*/

    /*@Test
    fun testExecuteStartConsSiteFalse() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event0 = ConstructionSiteEvent(0, 1, 3, false, road, 3, 2, 3)
        road.addEvent(event0)
        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)

        assertFalse(event.executeStart())
        assertFalse(road.eventList.contains(event))
        assertTrue(event.tick == 2)
        utils.clearEMCC()
        utils.clearSimulation()
    }*/

    /* @Test
     fun testStopConsSite() {
         val vertex0 = Vertex(0, null, 0)
         val vertex1 = Vertex(1, null, 1)

         val road =
             Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
         val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)
         road.addEvent(event)
         event.stopEvent()
         assertFalse(road.eventList.contains(event))
         utils.clearEMCC()
         utils.clearSimulation()
     }*/

    /*@Test
    fun testExecuteStartRoadClosureTrue() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val event = RoadClosureEvent(0, 1, 2, road)
        assertTrue(event.executeStart())
        assertTrue(road.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }*/

    /*@Test
    fun testExecuteRoadClosureFalse() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event0 = ConstructionSiteEvent(0, 1, 3, false, road, 3, 2, 3)
        road.addEvent(event0)
        val event = RoadClosureEvent(0, 1, 2, road)

        assertFalse(event.executeStart())
        assertFalse(road.eventList.contains(event))
        assertTrue(event.tick == 2)
        utils.clearEMCC()
        utils.clearSimulation()
    }*/

    @Test
    fun testStopRoadClosure() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = RoadClosureEvent(0, 1, 2, road)
        road.addEvent(event)
        event.stopEvent()
        assertFalse(road.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteTrafficJamTrue() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)

        val event = TrafficJamEvent(0, 1, 2, road, 3)
        assertTrue(event.executeStart())
        assertTrue(road.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteTrafficJamFalse() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event0 = ConstructionSiteEvent(0, 1, 3, false, road, 3, 2, 3)
        road.addEvent(event0)
        val event = TrafficJamEvent(0, 1, 2, road, 3)

        assertFalse(event.executeStart())
        assertFalse(road.eventList.contains(event))
        assertTrue(event.tick == 2)
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testStopTrafficJam() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)

        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = TrafficJamEvent(0, 1, 2, road, 3)
        road.addEvent(event)
        event.stopEvent()
        assertFalse(road.eventList.contains(event))
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteStartVehicleTrue() {
        val pos: Position? = null
        val vertex0 = Vertex(0, null, 0)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        base.addVehicle(vehicle)
        val event = VehicleUnavailableEvent(0, 1, 2, vehicle)
        assertTrue(event.executeStart())
        assertFalse(vehicle.available)
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testExecuteStartVehicleFalse() {
        val pos: Position? = null
        val vertex0 = Vertex(0, null, 0)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        vehicle.available = false
        base.addVehicle(vehicle)
        val event = VehicleUnavailableEvent(0, 1, 2, vehicle)
        assertFalse(event.executeStart())
        assertTrue(event.tick == 2)
        assertFalse(vehicle.available)
        utils.clearEMCC()
        utils.clearSimulation()
    }

    @Test
    fun testStopVehicle() {
        val pos: Position? = null
        val vertex0 = Vertex(0, null, 0)
        val base = Base(10, 20, vertex0, mutableListOf<Vehicle>())
        val vehicle = Vehicle(1, VehicleType.AMBULANCE, base, 10, 2, pos)
        vehicle.available = false
        base.addVehicle(vehicle)
        val event = VehicleUnavailableEvent(0, 1, 2, vehicle)

        event.stopEvent()
        assertTrue(vehicle.available)
        utils.clearEMCC()
        utils.clearSimulation()
    }
}
