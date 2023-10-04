package NotEnoughStaff

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class NotEnoughStaffTest : SystemTest() {
    override val name = "NotEnoughStaff"

    override val map = "NotEnoughStaff/map.dot"
    override val assets = "NotEnoughStaff/assets.json"
    override val scenario = "NotEnoughStaff/scenario.json"
    override val maxTicks = 100

    override suspend fun run() {
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 1 arrived at 1.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Allocation: 1 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 1 arrived at 4.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
    }
}
