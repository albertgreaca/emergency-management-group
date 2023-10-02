package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RequestTest : SystemTest() {
    override val name = "Request"

    override val map = "mapFiles/request_map.dot"
    override val assets = "assetsJsons/request_baseNvehicles.json"
    override val scenario = "scenarioJsons/request_scenario.json"
    override val maxTicks = 21

    override suspend fun run() {
        // everything is successfully parsed and validated
        assertNextLine("Initialization Info: request_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: request_baseNvehicles.json successfully parsed and validated")
        assertNextLine("Initialization Info: request_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        //police base numero uno e numero dos don't have the needed asset
        assertNextLine("Asset Request: 1 sent to 2 for 0")
        assertNextLine("Asset Request: 2 sent to 3 for 0")
        assertNextLine("Asset Allocated: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 0 arrived at 4")
        assertNextLine("Emergency Handling Start: 0 handling started")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Emergency Resolved: 0 resolved")
        // Simulation is over
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        // end of file
        assertEnd()






    }
}
