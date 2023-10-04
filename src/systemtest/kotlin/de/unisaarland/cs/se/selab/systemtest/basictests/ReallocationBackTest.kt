package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ReallocationBackTest : SystemTest() {
    override val name = "ReallocationBackTest"

    override val map = "mapFiles/ReallocationBackPartlyResources.dot"
    override val assets = "assetsJsons/reallocationBackAssets.json"
    override val scenario = "scenarioJsons/ReallocationBackEmergencies.json"
    override val maxTicks = 100

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: ReallocationBackPartlyResources.dot successfully parsed and validated")
        assertNextLine("Initialization Info: reallocationBackAssets.json successfully parsed and validated")
        assertNextLine("Initialization Info: ReallocationBackEmergencies.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 1 assigned to 2")
        assertNextLine("Asset Allocation: 1 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Reallocation: 0 reallocated to 1.")
        assertNextLine("Asset Request: 1 sent to 0 for 1.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Asset Arrival: 2 arrived at 3.")
        assertNextLine("Asset Arrival: 3 arrived at 3.")
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 5 arrived at 3.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Emergency Failed: 0 failed.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
