package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class Reallocation : SystemTest() {
    override val name = "Reallocation"

    override val map = "testReallocation/map_reallocate.dot"
    override val assets = "testReallocation/assets_reallocate.json"
    override val scenario = "testReallocation/scenario_reallocate.json"
    override val maxTicks = 20

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: map_reallocate.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets_reallocate.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario_reallocate.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        assertNextLine("Asset Allocation: 2 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Reallocation: 0 reallocated to 1.")
        assertNextLine("Asset Request: 1 sent to 2 for 1.")
        assertNextLine("Asset Allocation: 8 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 0 arrived at 1.")
        assertNextLine("Asset Arrival: 2 arrived at 1.")
        assertNextLine("Asset Arrival: 3 arrived at 1.")
        assertNextLine("Asset Arrival: 4 arrived at 1.")
        assertNextLine("Asset Arrival: 5 arrived at 1.")
        assertNextLine("Asset Arrival: 6 arrived at 1.")
        assertNextLine("Asset Arrival: 8 arrived at 3.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Asset Arrival: 0 arrived at 0.")
        assertNextLine("Asset Arrival: 2 arrived at 0.")
        assertNextLine("Asset Arrival: 3 arrived at 0.")
        assertNextLine("Asset Arrival: 4 arrived at 0.")
        assertNextLine("Asset Arrival: 5 arrived at 0.")
        assertNextLine("Asset Arrival: 6 arrived at 0.")
        assertNextLine("Asset Arrival: 8 arrived at 5.")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Asset Allocation: 4 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Simulation Tick: 13")
        assertNextLine("Asset Arrival: 4 arrived at 2.")
        continueWith()
    }

    suspend fun continueWith() {
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 14")
        assertNextLine("Simulation Tick: 15")
        assertNextLine("Simulation Tick: 16")
        assertNextLine("Emergency Resolved: 0 resolved.")
        // The Simulation should end
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        // end of file is reached
        assertEnd()
    }
}
