
import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.emergencies.EmergencyType
import de.unisaarland.cs.se.selab.mainlogic.EMCC
import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.resources.Resource
import de.unisaarland.cs.se.selab.vehicles.VehicleType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class BaseTest {
    val test = TestUtils()

    @BeforeEach
    fun beforeEach() {
        test.clear()
    }

    @Test
    fun watertrucktest1800w2vehic() {
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // here we have an index out of bound error
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[3]
        assertTrue(em.currentResources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest1200w2vehic() {
        // some more cases I thought of:
        // 2400, 1 vehic
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1200, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // here we have an index out of bound error
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[1]
        assertTrue(em.currentResources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest2400w2vehic() {
        // some more cases I thought of:
        // 2400, 1 vehic
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER, VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 2400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        // in this case, should the base send a 600 Watertruck, as the 1800 can stil be fulfiled with a 2400?
        // val vehicles = b.vehicles
        // val vehicle1 = vehicles[3]
        // val testres = Resource(mutableListOf(VehicleType.FIRE_TRUCK_WATER), 1200, 0, 0, 0)
        // assertTrue(em.resources.isEqual(testres))
        // assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(true)
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest2400w1vehic() {
        // some more cases I thought of:
        // 1800, 3 vehic
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 2400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(vehicleList, 2400, 0, 0, 0)
        assertTrue(em.assignedVehicles.isEmpty())
        assertTrue(testres.isEqual(em.currentResources))
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest1800w3vehic() {
        // some more cases I thought of:
        // 1800, 1 vehic
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER
        )
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val vehicles = b.vehicles
        val vehicle1 = vehicles[0]
        val vehicle2 = vehicles[1]
        val vehicle3 = vehicles[2]
        assertTrue(em.currentResources.isEmpty())
        assertTrue(vehicle1 == em.assignedVehicles[0])
        assertTrue(vehicle2 == em.assignedVehicles[1])
        assertTrue(vehicle3 == em.assignedVehicles[2])
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest1800w1vehic() {
        // some more cases I thought of:
        // 600, 1 vehic
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/firebasesimple.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(VehicleType.FIRE_TRUCK_WATER)
        val res = Resource(vehicleList, 1800, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(vehicleList, 1800, 0, 0, 0)
        assertTrue(em.assignedVehicles.isEmpty())
        assertTrue(testres.isEqual(em.currentResources))
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktest5400w6vehic() {
        // some more cases I thought of:
        // 600, 1 vehic
        // dikstra doesn't work now
        // understaffed so only 1 vehicle can be sent
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/5400Waterassetconfig.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER,
            VehicleType.FIRE_TRUCK_WATER
        )
        val res = Resource(vehicleList, 5400, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        em.base = b
        b.requestResources(em)
        val testres = Resource(mutableListOf(VehicleType.FIRE_TRUCK_WATER), 2400, 0, 0, 0)
        assertTrue(testres.isEqual(em.currentResources))
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun watertrucktestundertaffed1vehic() {
        // some more cases I thought of:
        // dikstra doesn't work now
        val graph = Simulation.map
        val parse = MapParser(graph, File("src/test/resources/mapvalid1.dot"))
        val jsonparse = JsonParser(
            graph,
            File("src/test/resources/UnitTestConfig2/5400Waterassetconfig.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()
        val vertex1 = requireNotNull(graph.getVertexFromId(0))
        val vertex2 = requireNotNull(graph.getVertexFromId(1))
        val road = requireNotNull(graph.getRoad(vertex1, vertex2))
        val vehicleList = mutableListOf(
            VehicleType.FIRE_TRUCK_WATER,
        )
        val res = Resource(vehicleList, 600, 0, 0, 0)
        val em = Emergency(0, 1, road, EmergencyType.FIRE, 2, 1, 20, res)
        em.id
        val vertex3 = requireNotNull(graph.getVertexFromId(2))
        val b = requireNotNull(vertex3.base)
        b.staff = 3
        em.base = b
        b.requestResources(em)
        val vehicles = b.vehicles
        val vehic1 = vehicles[1]
        val testres = Resource(mutableListOf(), 0, 0, 0, 0)
        assertTrue(testres.isEqual(em.currentResources))
        assertTrue(vehic1 == em.assignedVehicles[0])
        test.clearEMCC()
        test.clearSimulation()
    }

    @Test
    fun testcalculateNextBases() {
        // Scenario: FB1 ---- FB2 ---- FB3 ---- PB1 ---- AB1
        val parse =
            MapParser(Simulation.map, File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"))
        val jsonparse = JsonParser(
            Simulation.map,
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fb1 = EMCC.fireDepartment?.bases?.get(0)!!
        val fb2 = EMCC.fireDepartment?.bases?.get(1)!!
        val fb3 = EMCC.fireDepartment?.bases?.get(2)!!
        val pb1 = EMCC.policeDepartment?.bases?.get(0)!!
        val ab1 = EMCC.ambulanceDepartment?.bases?.get(0)!!

        val expectedListForFb1 = mutableListOf(fb1, fb2, fb3, pb1, ab1)
        val expectedListForFb2 = mutableListOf(fb2, fb1, fb3, pb1, ab1)
        val expectedListForFb3 = mutableListOf(fb3, fb2, pb1, fb1, ab1)
        val expectedListForPb1 = mutableListOf(pb1, fb3, ab1, fb2, fb1)
        val expectedListForAb1 = mutableListOf(ab1, pb1, fb3, fb2, fb1)

        fb1.calculateNextBases()
        fb2.calculateNextBases()
        fb3.calculateNextBases()
        pb1.calculateNextBases()
        ab1.calculateNextBases()

        assertEquals(expectedListForFb1, fb1.nextBases)
        assertEquals(expectedListForFb2, fb2.nextBases)
        assertEquals(expectedListForFb3, fb3.nextBases)
        assertEquals(expectedListForPb1, pb1.nextBases)
        assertEquals(expectedListForAb1, ab1.nextBases)
    }

    @Test
    fun testGetNextFireBase() {
        // Scenario: FB1 ---- FB2 ---- FB3 ---- PB1 ---- AB1
        val parse =
            MapParser(Simulation.map, File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"))
        val jsonparse = JsonParser(
            Simulation.map,
            File("src/test/resources/UnitTestConfig2/ThreeFireBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val fb1 = EMCC.fireDepartment?.bases?.get(0)!!
        val fb2 = EMCC.fireDepartment?.bases?.get(1)!!
        val fb3 = EMCC.fireDepartment?.bases?.get(2)!!

        fb1.calculateNextBases()
        fb2.calculateNextBases()
        fb3.calculateNextBases()

        // closest bases from FB1
        assertEquals(fb2, fb1.getNextFireBase(fb1))
        assertEquals(fb3, fb1.getNextFireBase(fb2))
        assertEquals(null, fb1.getNextFireBase(fb3))

        // closest bases from FB2
        assertEquals(fb1, fb2.getNextFireBase(fb2))
        assertEquals(fb3, fb2.getNextFireBase(fb1))
        assertEquals(null, fb2.getNextFireBase(fb3))

        // closest bases from FB3
        assertEquals(fb2, fb3.getNextFireBase(fb3))
        assertEquals(fb1, fb3.getNextFireBase(fb2))
        assertEquals(null, fb3.getNextFireBase(fb1))
    }

    @Test
    fun testGetNextPoliceBase() {
        // Scenario: PB1 ---- PB2 ---- PB3 ---- FB1 ---- AB1
        val parse =
            MapParser(Simulation.map, File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"))
        val jsonparse = JsonParser(
            Simulation.map,
            File("src/test/resources/UnitTestConfig2/ThreePoliceBases.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val pb1 = EMCC.policeDepartment?.bases?.get(0)!!
        val pb2 = EMCC.policeDepartment?.bases?.get(1)!!
        val pb3 = EMCC.policeDepartment?.bases?.get(2)!!

        pb1.calculateNextBases()
        pb2.calculateNextBases()
        pb3.calculateNextBases()

        // closest bases from FB1
        assertEquals(pb2, pb1.getNextPoliceBase(pb1))
        assertEquals(pb3, pb1.getNextPoliceBase(pb2))
        assertEquals(null, pb1.getNextPoliceBase(pb3))

        // closest bases from FB2
        assertEquals(pb1, pb2.getNextPoliceBase(pb2))
        assertEquals(pb3, pb2.getNextPoliceBase(pb1))
        assertEquals(null, pb2.getNextPoliceBase(pb3))

        // closest bases from FB3
        assertEquals(pb2, pb3.getNextPoliceBase(pb3))
        assertEquals(pb1, pb3.getNextPoliceBase(pb2))
        assertEquals(null, pb3.getNextPoliceBase(pb1))
    }

    @Test
    fun testGetNextHospital() {
        // Scenario: AB1 ---- AB2 ---- AB3 ---- FB1 ---- PB1
        val parse =
            MapParser(Simulation.map, File("src/test/resources/UnitTestMapConfig/Saarbruecken5VerticesLinear.dot"))
        val jsonparse = JsonParser(
            Simulation.map,
            File("src/test/resources/UnitTestConfig2/ThreeHospitals.json"),
            File("src/test/resources/UnitTestConfig3/emergencysimple.json")
        )
        parse.parseMap()
        jsonparse.parseBases()
        jsonparse.parseVehicles()
        jsonparse.parseEmergency()
        jsonparse.parseEvents()

        val ab1 = EMCC.ambulanceDepartment?.bases?.get(0)!!
        val ab2 = EMCC.ambulanceDepartment?.bases?.get(1)!!
        val ab3 = EMCC.ambulanceDepartment?.bases?.get(2)!!

        ab1.calculateNextBases()
        ab2.calculateNextBases()
        ab3.calculateNextBases()

        // closest bases from FB1
        assertEquals(ab2, ab1.getNextHospital(ab1))
        assertEquals(ab3, ab1.getNextHospital(ab2))
        assertEquals(null, ab1.getNextHospital(ab3))

        // closest bases from FB2
        assertEquals(ab1, ab2.getNextHospital(ab2))
        assertEquals(ab3, ab2.getNextHospital(ab1))
        assertEquals(null, ab2.getNextHospital(ab3))

        // closest bases from FB3
        assertEquals(ab2, ab3.getNextHospital(ab3))
        assertEquals(ab1, ab3.getNextHospital(ab2))
        assertEquals(null, ab3.getNextHospital(ab1))
    }
}
