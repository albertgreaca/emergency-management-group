package de.unisaarland.cs.se.selab.systemtest.vehicleattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EDCCriminalTest : SystemTest() {
    override val name = "EDCCriminalTest"

    override val map = "mapFiles/example_map.dot"
    override val assets = "jsonvehiclesproperties/EDCCriminal.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: EDCCriminal.json invalid")
    }
}
