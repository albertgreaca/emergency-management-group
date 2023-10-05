package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class MultipleReallocationsTest : SystemTest() {

    override val name = "MultipleReallocationsTest"

    override val map = "testcases/multipleReallocations/map.dot"
    override val assets = "testcases/multipleReallocations/config2.json"
    override val scenario = "testcases/multipleReallocations/config3.json"
    override val maxTicks = 30
    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: ReallocationBackPartlyResources.dot successfully parsed and validated")
        assertNextLine("Initialization Info: reallocationBackAssets2.json successfully parsed and validated")
        assertNextLine("Initialization Info: ReallocationBackEmergencies2.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")
        assertNextLine("Simulation Tick: 2")
        // em0 is assigned to hospital (id 0)
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // amb0 allocated to em0, needs 2 ticks
        assertNextLine("Asset Allocation: 0 allocated to 0; 2 ticks to arrive.")
        assertNextLine("Simulation Tick: 3")
        // em1 is assigned to hospital
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        // amb1, amb2, and emDocCar5 are allocated to em1
        assertNextLine("Asset Allocation: 1 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 4 ticks to arrive.")
        assertNextLine("Simulation Tick: 4")
        // em2 is assigned to hospital
        assertNextLine("Emergency Assignment: 2 assigned to 0")
        // amb3, amb4, emDocCar6, and emDocCar7 are allocated to em2, 3 ambs and 2 fire trucks techn. are still missing
        assertNextLine("Asset Allocation: 3 allocated to 2; 6 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 2; 6 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 2; 6 ticks to arrive.")
        assertNextLine("Asset Allocation: 7 allocated to 2; 6 ticks to arrive.")
        // reallocate amb0 from em0 and amb1, amb2 from em1
        assertNextLine("Asset Reallocation: 0 reallocated to 2.")
        assertNextLine("Asset Reallocation: 1 reallocated to 2.")
        assertNextLine("Asset Reallocation: 2 reallocated to 2.")
        // make request to fire station (id 2) for ftt8 and ftt9
        assertNextLine("Asset Request: 1 sent to 2 for 2.")
        // request succeeded, ftt8 and ftt9 are allocated to em2, 4 ticks needed
        assertNextLine("Asset Allocation: 8 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Asset Allocation: 9 allocated to 2; 4 ticks to arrive.")
        assertNextLine("Simulation Tick: 5")
        assertNextLine("Simulation Tick: 6")
        // em0 fails since it has not been started handling by tick 6
        assertNextLine("Emergency Failed: 0 failed.")
        assertNextLine("Simulation Tick: 7")
        // emDocCar arrives at em1
        assertNextLine("Asset Arrival: 5 arrived at 2.")
        assertNextLine("Simulation Tick: 8")
        // amb0, ftt8, and ftt9 arrive at em2
        assertNextLine("Asset Arrival: 0 arrived at 3.")
        assertNextLine("Asset Arrival: 8 arrived at 3.")
        assertNextLine("Asset Arrival: 9 arrived at 3.")
        assertNextLine("Simulation Tick: 9")
        // amb1 and amb2 arrive at em2
        assertNextLine("Asset Arrival: 1 arrived at 3.")
        assertNextLine("Asset Arrival: 2 arrived at 3.")
        assertNextLine("Simulation Tick: 10")
        // amb3, amb4, emDocCar6, and emDocCar7 arrive at em2
        assertNextLine("Asset Arrival: 3 arrived at 3.")
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        assertNextLine("Asset Arrival: 6 arrived at 3.")
        assertNextLine("Asset Arrival: 7 arrived at 3.")
        assertNextLine("Emergency Handling Start: 2 handling started.")
        assertNextLine("Simulation Tick: 11")
        // em1 fails since it has not been started handling by tick 11 (emDocCar5 is driving back)
        assertNextLine("Emergency Failed: 1 failed.")
        assertNextLine("Simulation Tick: 12")
        assertNextLine("Simulation Tick: 13")
        assertNextLine("Simulation Tick: 14")
        // em2 is resolved in tick 14, this is end of simulation since it was the last emergency
        assertNextLine("Emergency Resolved: 2 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 3 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        assertNextLine("Simulation Statistics: 2 failed emergencies.")
        assertNextLine("Simulation Statistics: 1 resolved emergencies.")
        assertEnd()
    }
}
