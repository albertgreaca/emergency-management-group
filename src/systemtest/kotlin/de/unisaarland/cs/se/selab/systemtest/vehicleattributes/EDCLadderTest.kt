package de.unisaarland.cs.se.selab.systemtest.vehicleattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EDCLadderTest : SystemTest() {
    override val name = "EDCLadder"

    override val map = "mapFiles/example_map.dot"
    override val assets = "jsonvehiclesproperties/EDCLadder.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: EDCLadder.json invalid")
    }
}
