package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class iDontLikeHeights : SystemTest() {
    override val name = "iDontLikeHeights"

    override val map = "iDontLikeHeights/map.dot"
    override val assets = "iDontLikeHeights/assets.json"
    override val scenario = "iDontLikeHeights/scenario.json"
    override val maxTicks = 42

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 1 allocated to 0; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 2 arrived at 4.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 1 arrived at 4.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Emergency Resolved: 0 resolved.")
        // The Simulation should end
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        // end of file is reached
        assertEnd()
    }
}
