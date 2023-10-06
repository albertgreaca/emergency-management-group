package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class LessLadderTest : SystemTest() {
    override val name = "LessLadderTest"

    override val map = "LessLadder/llmap.dot"
    override val assets = "LessLadder/llassets.json"
    override val scenario = "LessLadder/llscenario.json"
    override val maxTicks = 20
    val stringconst = "Request Failed: 0 failed."

    override suspend fun run() {
        assertNextLine("Initialization Info: llmap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: llassets.json successfully parsed and validated")
        assertNextLine("Initialization Info: llscenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 3 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 7 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 10 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 11 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 13 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Request: 1 sent to 1 for 0.")
        // requesting ambulance
        assertNextLine("Asset Request: 2 sent to 3 for 0.")
        assertNextLine("Asset Allocation: 14 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 3 arrived at 5.")
        assertNextLine("Asset Arrival: 4 arrived at 5.")
        assertNextLine("Asset Arrival: 6 arrived at 5.")
        assertNextLine("Asset Arrival: 7 arrived at 5.")
        assertNextLine("Asset Arrival: 8 arrived at 5.")
        assertNextLine("Asset Arrival: 9 arrived at 5.")
        assertNextLine("Asset Arrival: 10 arrived at 5.")
        assertNextLine("Asset Arrival: 11 arrived at 5.")
        assertNextLine("Asset Arrival: 13 arrived at 5.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 1 arrived at 5.")
        assertNextLine("Asset Arrival: 2 arrived at 5.")
        assertNextLine("Asset Arrival: 5 arrived at 5.")
        assertNextLine("Asset Arrival: 14 arrived at 5.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 0 resolved.")
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
