package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RequestTestPartial : SystemTest() {
    override val name = "Request"

    override val map = "mapFiles/request_map.dot"
    override val assets = "assetsJsons/request_baseNvehiclesPartial.json"
    override val scenario = "scenarioJsons/request_scenario_partial.json"
    override val maxTicks = 30
    private val fail = "Request Failed: 0 failed."

    override suspend fun run() {
        assertNextLine("Initialization Info: request_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: request_baseNvehiclesPartial.json successfully parsed and validated")
        assertNextLine("Initialization Info: request_scenario_partial.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Request: 1 sent to 2 for 0.")
        assertNextLine("Asset Allocation: 0 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Request: 2 sent to 3 for 0.")
        assertNextLine(fail)
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Request: 3 sent to 2 for 0.")
        assertNextLine("Asset Request: 4 sent to 3 for 0.")
        assertNextLine(fail)
        assertNextLine("Asset Arrival: 0 arrived at 1.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Request: 5 sent to 2 for 0.")
        assertNextLine("Asset Request: 6 sent to 3 for 0.")
        assertNextLine(fail)
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Request: 7 sent to 2 for 0.")
        assertNextLine("Asset Request: 8 sent to 3 for 0.")
        assertNextLine(fail)
        assertNextLine("Emergency Failed: 0 failed.")
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 0 resolved emergencies.")
        // end of file
        assertEnd()
    }
}
