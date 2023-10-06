package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class CriminalCapacityChoose : SystemTest() {
    override val name = "CriminalCapacityChoose"

    override val map = "CriminalCapacityChoose/map.dot"
    override val assets = "CriminalCapacityChoose/assets.json"
    override val scenario = "CriminalCapacityChoose/scenario.json"
    override val maxTicks = 200

    override suspend fun run() {
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assets.json successfully parsed and validated")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Request: 1 sent to 1 for 0.")
        assertNextLine("Asset Request: 2 sent to 2 for 0.")
        assertNextLine("Asset Request: 3 sent to 3 for 0.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 7 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 10 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 11 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 12 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 13 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Arrival: 4 arrived at 7.")
        assertNextLine("Asset Arrival: 5 arrived at 7.")
        assertNextLine("Asset Arrival: 6 arrived at 7.")
        assertNextLine("Asset Arrival: 7 arrived at 7.")
        assertNextLine("Asset Arrival: 8 arrived at 7.")
        assertNextLine("Asset Arrival: 9 arrived at 7.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 0 arrived at 6.")
        assertNextLine("Asset Arrival: 10 arrived at 7.")
        assertNextLine("Asset Arrival: 11 arrived at 7.")
        assertNextLine("Asset Arrival: 12 arrived at 7.")
        assertNextLine("Asset Arrival: 13 arrived at 7.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 1 arrived at 6.")
        assertNextLine("Asset Arrival: 2 arrived at 6.")
        assertNextLine("Asset Arrival: 3 arrived at 6.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 1 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
    }
}
