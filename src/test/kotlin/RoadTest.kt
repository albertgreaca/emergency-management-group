
import de.unisaarland.cs.se.selab.events.ConstructionSiteEvent
import de.unisaarland.cs.se.selab.events.RushHourEvent
import de.unisaarland.cs.se.selab.events.TrafficJamEvent
import de.unisaarland.cs.se.selab.graphlogic.PrimaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Road
import de.unisaarland.cs.se.selab.graphlogic.SecondaryRoadType
import de.unisaarland.cs.se.selab.graphlogic.Vertex
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoadTest {

    @Test
    fun testAdd() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)

        road.addEvent(event)
        assertTrue(road.eventList.contains(event))
    }

    @Test
    fun testRemove() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)

        road.eventList.add(event)
        road.removeEvent(event)
        assertFalse(road.eventList.contains(event))
    }

    @Test
    fun testCurrentEventEmpty() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        assertTrue(road.getCurrentEvent() == null)
    }

    @Test
    fun testCurrentEventFull() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)
        road.eventList.add(event)
        assertTrue(road.getCurrentEvent() == event)
    }

    @Test
    fun testActualWeightEmpty() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        assertTrue(road.getActualWeight() == 20)
    }

    @Test
    fun testActualWeightConsSite() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = ConstructionSiteEvent(0, 1, 2, false, road, 3, 0, 1)
        road.eventList.add(event)
        assertTrue(road.getActualWeight() == 60)
    }

    @Test
    fun testActualWeightRushHour() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val vertex2 = Vertex(2, null, 2)
        val vertex3 = Vertex(3, null, 3)
        val road1 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 10, 4, vertex0, vertex1)
        val road2 =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Allee", 20, 4, vertex2, vertex3)
        val roadlist = mutableListOf(road1, road2)
        val event = RushHourEvent(1, 2, 2, roadlist, 2)
        road1.addEvent(event)
        road2.addEvent(event)
        assertTrue(road1.getActualWeight() == 20)
        assertTrue(road2.getActualWeight() == 40)
    }

    @Test
    fun testActualWeightTrafficJam() {
        val vertex0 = Vertex(0, null, 0)
        val vertex1 = Vertex(1, null, 1)
        val road =
            Road(PrimaryRoadType.MAINSTREET, SecondaryRoadType.NONE, "Saarbruecken", "Weg", 20, 4, vertex0, vertex1)
        val event = TrafficJamEvent(0, 1, 2, road, 3)
        road.eventList.add(event)
        assertTrue(road.getActualWeight() == 60)
    }

    @Test
    fun testEqualsTrue() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        var r1 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Village", "Street", 20, 4, v1, v2)
        var r2 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Village", "Street", 20, 4, v1, v2)
        assertTrue(r1.equals(r2))
        assertTrue(r2.equals(r1))
    }

    @Test
    fun testEqualsFalse() {
        val v1 = Vertex(0, null, 0)
        val v2 = Vertex(1, null, 1)
        var r1 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Village", "Street", 20, 4, v1, v2)
        var r2 = Road(PrimaryRoadType.SIDESTREET, SecondaryRoadType.NONE, "Village", "Street", 20, 4, v2, v1)
        assertFalse(r1.equals(r2))
        assertFalse(r2.equals(r1))
    }
}
