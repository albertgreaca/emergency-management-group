
import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RushHourNoCounty : SystemTest() {
    override val name = "RushHourNoCounty"

    override val map = "mapFiles/RushHourMap.dot"
    override val assets = "assetsJsons/AssetsRushHour1.json"
    override val scenario = "invalidConfig3/mutants/RushHourNoCounty.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: RushHourMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsRushHour1.json successfully parsed and validated")
        assertNextLine("Initialization Info: RushHourNoCounty.json invalid")
    }
}
