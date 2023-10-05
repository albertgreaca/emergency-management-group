package de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.vehiclesWrongBase

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class FTPatPolice : SystemTest() {
    override val name = "FTPatPolice"

    override val map = "mapFiles/example_map.dot"
    override val assets = "config2Invalid/VehicleWrongBase/FTPatPolice.json"
    override val scenario = "scenarioJsons/example_scenario.json"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: example_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: FTPatPolice.json invalid")
    }
}
