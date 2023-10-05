package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RequestAgainStaff : SystemTest() {
    override val name = "RequestAgainStaff"

    override val map = "RequestAgainStaff/map.dot"
    override val assets = "RequestAgainStaff/assets.json"
    override val scenario = "RequestAgainStaff/scenario.json"
    override val maxTicks = 200

    override suspend fun run() {
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 2 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Arrival: 2 arrived at 1.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        assertNextLine("Asset Allocation: 0 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Request: 1 sent to 1 for 1.")
        assertNextLine("Asset Request: 2 sent to 2 for 1.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 3 arrived at 6.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
    }
}
