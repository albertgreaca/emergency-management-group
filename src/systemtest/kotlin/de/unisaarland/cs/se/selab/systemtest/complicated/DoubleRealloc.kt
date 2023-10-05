package de.unisaarland.cs.se.selab.systemtest.complicated

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class DoubleRealloc : SystemTest() {
    override val name = "DoubleRealloc"

    override val map = "mapFiles/doubleRealloc.dot"
    override val assets = "assetsJsons/AssetsDoubleRealloc.json"
    override val scenario = "scenarioJsons/EmergDoubleRealloc.json"
    override val maxTicks = 40

    override suspend fun run() {
        assertNextLine("Initialization Info: doubleRealloc.dot successfully parsed and validated")
        assertNextLine("Initialization Info: AssetsDoubleRealloc.json successfully parsed and validated")
        assertNextLine("Initialization Info: EmergDoubleRealloc.json successfully parsed and validated")
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Emergency Assignment: 0 assigned to 2")
        assertNextLine("Asset Allocation: 3 allocated to 0; 3 ticks to arrive.")
        assertNextLine("Simulation Tick: 2")
        assertNextLine("Emergency Assignment: 1 assigned to 2")
        assertNextLine("Asset Allocation: 4 allocated to 1; 3 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 3 ticks to arrive.")
        assertNextLine("Asset Reallocation: 3 reallocated to 1.")
        assertNextLine("Asset Request: 1 sent to 3 for 0.") // request for sev 0 em (ambulancia was reallocated)
        assertNextLine("Asset Allocation: 7 allocated to 0; 4 ticks to arrive.") // ambulance for sev 0
        assertNextLine("Simulation Tick: 3")
        assertNextLine("Emergency Assignment: 2 assigned to 2") // process severity 3 emergency
        assertNextLine("Asset Allocation: 6 allocated to 2; 3 ticks to arrive.")
        assertNextLine("Asset Reallocation: 3 reallocated to 2.")
        assertNextLine("Asset Reallocation: 4 reallocated to 2.")
        assertNextLine("Asset Reallocation: 5 reallocated to 2.")
        assertNextLine("Asset Request: 2 sent to 1 for 2.")
        assertNextLine("Asset Request: 3 sent to 3 for 2.")
        assertNextLine("Asset Request: 4 sent to 3 for 1.")
        assertNextLine("Asset Allocation: 1 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 10 allocated to 2; 4 ticks to arrive.") // now the 3 ambulancias for sev 2
        assertNextLine("Asset Allocation: 11 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 12 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 13 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        assertNextLine("Asset Arrival: 3 arrived at 7.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Asset Arrival: 4 arrived 7.")
        assertNextLine("Asset Arrival: 5 arrived 7.")
        assertNextLine("Simulation Tick: 6")
        assertNextLine("Asset Arrival: 6 arrived 7.")
        assertNextLine("Asset Arrival: 7 arrived 3.")
        assertNextLine("Emergency Handling Start: 0 handling started.")
        assertNextLine("Simulation Tick: 7")
        assertNextLine("Asset Arrival: 1 arrived 7.")
        assertNextLine("Asset Arrival: 2 arrived 7.")
        assertNextLine("Asset Arrival: 8 arrived 7.")
        assertNextLine("Asset Arrival: 9 arrived 7.")
        assertNextLine("Asset Arrival: 10 arrived 7.")
        assertNextLine("Asset Arrival: 11 arrived 5.")
        assertNextLine("Asset Arrival: 12 arrived 5.")
        assertNextLine("Asset Arrival: 13 arrived 5.")
        assertNextLine("Emergency Handling Start: 1 handling started.")
        assertNextLine("Emergency Handling Start: 2 handling started.")
        assertNextLine("Simulation Tick: 8")
        continueWith()
    }

    private suspend fun continueWith() {
        assertNextLine("Emergency Resolved: 0 resolved.")
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation Tick: 9")
        assertNextLine("Emergency Resolved: 2 resolved.")
        // Simulation is over
        assertNextLine("Simulation End")
        // Statistics
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 3 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 3 resolved emergencies.")
        // end of file
        assertEnd()
    }
}
