package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class VehUnavPp : SystemTest() {
    override val name = "VehUnavPp"

    override val map = "mapFiles/ThreeVertexMap.dot"
    override val assets = "assetsJsons/assetsVehUnav.json"
    override val scenario = "scenarioJsons/assetsVehUnavScen.json"
    override val maxTicks = 100

    override suspend fun run() {
        assertNextLine("Initialization Info: ThreeVertexMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: assetsVehUnav.json successfully parsed and validated")
        assertNextLine("Initialization Info: assetsVehUnavScen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 1 assigned to 0; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Asset Allocation: 10 assigned to 1; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")

        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        assertEnd()
    }
}
