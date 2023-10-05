package de.unisaarland.cs.se.selab.systemtest.vehicleattributes

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class K9CriminalTest : SystemTest() {
    override val name = "K9CriminalTest"

    override val map = "mapFiles/example_map.dot"
    override val assets = "jsonvehiclesproperties/K9Criminal.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: K9Criminal.json invalid")
    }
}
