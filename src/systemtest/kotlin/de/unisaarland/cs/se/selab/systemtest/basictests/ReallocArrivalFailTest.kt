package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ReallocArrivalFailTest : SystemTest() {
    override val name = "ReallocArrivalFailTest"

    override val map = "mapFiles/ReallocationArrivalFail.dot"
    override val assets = "assetsJsons/reallocationArrivalFailBases.json"
    override val scenario = "scenarioJsons/reallocArrivalFailScen.json"
    override val maxTicks = 100

    override suspend fun run() {
        assertNextLine("Initialization Info: ReallocationArrivalFail.dot successfully parsed and validated")
        assertNextLine("Initialization Info: reallocationArrivalFailBases.json successfully parsed and validated")
        assertNextLine("Initialization Info: reallocArrivalFailScen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 10 allocated to 0; 7 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Emergency Failed: 1 failed.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Asset Arrival: 10 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 1 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
