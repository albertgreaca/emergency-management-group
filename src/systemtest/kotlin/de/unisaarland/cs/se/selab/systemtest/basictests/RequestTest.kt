package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RequestTest : SystemTest() {
    override val name = "Request"

    override val map = "mapFiles/rerouting_map.dot"
    override val assets = "assetsJsons/reroute_assets.json"
    override val scenario = "scenarioJsons/rerouting_scenario.json"
    override val maxTicks = 21

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: rerouting_map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: reroute_assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: rerouting_scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        // The Simulation should end
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 18 allocated to 0; 102 ticks to arrive.")
        assertNextLine("Asset Allocation: 22 allocated to 0; 102 ticks to arrive.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Assets Rerouted: 2")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 18 arrived at 6.")
        assertNextLine("Asset Arrival: 22 arrived at 6.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 2 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        // end of file is reached
        assertEnd()
    }
}
