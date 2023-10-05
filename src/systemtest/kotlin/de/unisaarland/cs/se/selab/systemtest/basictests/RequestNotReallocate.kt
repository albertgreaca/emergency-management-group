package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class RequestNotReallocate : SystemTest() {
    override val name = "RequestNotReallocate"

    override val map = "RequestNotReallocateRes/rnrmap.dot"
    override val assets = "RequestNotReallocateRes/rnrassets.json"
    override val scenario = "RequestNotReallocateRes/rnrscenario.json"
    override val maxTicks = 20
    val stringsave = "Request Failed: 1 failed."

    override suspend fun run() {
        assertNextLine("Initialization Info: rnrmap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: rnrassets.json successfully parsed and validated")
        assertNextLine("Initialization Info: rnrscenario.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Asset Allocation: 4 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Asset Allocation: 0 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 2 ticks to arrive.")
        // requesting police car
        assertNextLine("Asset Request: 1 sent to 2 for 1.")
        // requesting ambulance
        assertNextLine("Asset Request: 2 sent to 3 for 1.")
        assertNextLine(stringsave)
        assertNextLine("Asset Allocation: 5 allocated to 1; 1 ticks to arrive.")
        assertNextLine(stringsave)
        assertNextLine("Asset Arrival: 4 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Request: 3 sent to 2 for 1.")
        assertNextLine(stringsave)
        assertNextLine("Asset Arrival: 5 arrived at 5.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Request: 4 sent to 2 for 1.")
        assertNextLine(stringsave)
        assertNextLine("Asset Arrival: 0 arrived at 6.")
        assertNextLine("Asset Arrival: 1 arrived at 6.")
        assertNextLine("Asset Arrival: 2 arrived at 6.")
        assertNextLine("Asset Arrival: 3 arrived at 6.")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Request: 5 sent to 2 for 1.")
        assertNextLine(stringsave)
        assertNextLine("Emergency Failed: 1 failed.")
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        // end of file
        assertEnd()
    }
}
