package de.unisaarland.cs.se.selab.systemtest.vehicleattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class LadderCriminalTest : SystemTest() {
    override val name = "LadderCriminal"

    override val map = "mapFiles/example_map.dot"
    override val assets = "jsonvehiclesproperties/LadderCriminal.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: LadderCriminal.json invalid")
    }
}
