package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class EmergencySameTickTest : SystemTest() {
    override val name = "EmergencySameTickTest"

    override val map = "mapFiles/ThreeVertexMap.dot"
    override val assets = "assetsJsons/EmId.json"
    override val scenario = "scenarioJsons/EmIdScen.json"
    override val maxTicks = 22

    override suspend fun run() {
        // Everything is successfully parsed and validated
        assertNextLine("Initialization Info: ThreeVertexMap.dot successfully parsed and validated")
        assertNextLine("Initialization Info: EmId.json successfully parsed and validated")
        assertNextLine("Initialization Info: EmIdScen.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        // Two emergencies assigned to the same fire base
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Emergency Assignment: 1 assigned to 2")
        assertNextLine("Asset Allocation: 10 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 20 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Asset Arrival: 10 arrived at 2.")
        assertNextLine("Asset Arrival: 20 arrived at 2.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Emergency Resolved: 1 resolved.")
        // Simulation is over
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        // end of file
        assertEnd()
    }
}
