package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class ReallocUnav : SystemTest() {
    override val name = "DijTieSimple"

    override val map = "mapFiles/FiveVertexMap.dot"
    override val assets = "assetsJsons/reallocUnavAsset.json"
    override val scenario = "scenarioJsons/reallocUnavScen.json"
    override val maxTicks = 20

    override suspend fun run() {
        assertNextLine("Initialization Info: FiveVertexMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: reallocUnavAsset.json successfully parsed and validated")
        assertNextLine("Initialization Info: reallocUnavScen.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 1")
        assertNextLine("Asset Allocation: 1 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Emergency Assignment: 1 assigned to 1")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 1 arrived at 1.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Simulation Tick: 8")
        assertNextLine("Event Triggered: 0 triggered.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        assertNextLine("Simulation Tick: 11")
        assertNextLine("Event Ended: 0 ended.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Asset Allocation: 1 allocated to 1; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 13")
        assertNextLine("Simulation Tick: 14")
        assertNextLine("Simulation Tick: 15")
        assertNextLine("Asset Arrival: 1 arrived at 4.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 16")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        assertEnd()
    }
}
