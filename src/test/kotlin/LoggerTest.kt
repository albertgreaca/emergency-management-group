import de.unisaarland.cs.se.selab.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LoggerTest {
    @Test
    fun testLogInitInfoValid() {
        val expected = "Initialization Info: myFile.txt successfully parsed and validated"
        val actual = Logger.logInitInfo("myFile.txt", true)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogInitInfoInvalid() {
        val expected = "Initialization Info: myFile2.txt invalid"
        val actual = Logger.logInitInfo("myFile2.txt", false)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogSimulationStart() {
        val expected = "Simulation starts"
        val actual = Logger.logSimulationStart()
        assertEquals(expected, actual)
    }

    @Test
    fun testLogSimulationTick() {
        val expected = "Simulation Tick: 3"
        val actual = Logger.logSimulationTick(3)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogEmergencyAssignment() {
        val expected = "Emergency Assignment: 4 assigned to 7"
        val actual = Logger.logEmergencyAssignment(4, 7)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogAssetAllocation() {
        val expected = "Asset Allocation: 23 allocated to 6; 19 ticks to arrive."
        val actual = Logger.logAssetAllocation(23, 6, 19)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogAssetReallocation() {
        val expected = "Asset Reallocation: 15 reallocated to 3."
        val actual = Logger.logAssetReallocation(15, 3)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogAssetRequest() {
        val expected = "Asset Request: 12 sent to 82 for 29."
        val actual = Logger.logAssetRequest(12, 82, 29)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogRequestFailed() {
        val expected = "Request Failed: 18 failed."
        val actual = Logger.logRequestFailed(18)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogAssetArrival() {
        val expected = "Asset Arrival: 32 arrived at 19."
        val actual = Logger.logAssetArrival(32, 19)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogEmergencyHandlingStart() {
        val expected = "Emergency Handling Start: 25 handling started."
        val actual = Logger.logEmergencyHandlingStart(25)
        assertEquals(expected, actual)
    }

    @Test
    fun testLogEmergencyResolved() {
        val expected = "Emergency Resolved: 38 resolved."
        val actual = Logger.logEmergencyResolved(38)
        assertEquals(expected, actual)
    }


}