package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class LessDogsThanK9Cars : SystemTest() {
    override val name = "LessDogsThanK9Cars"

    override val map = "testcases/lessK9DogsThanK9Cars/map.dot"
    override val assets = "testcases/lessK9DogsThanK9Cars/config2.json"
    override val scenario = "testcases/lessK9DogsThanK9Cars/config3.json"
    override val maxTicks = 42

    override suspend fun run() {
        // everything is parsed and validated
        assertNextLine("Initialization Info: map.dot successfully parsed and validated")
        assertNextLine("Initialization Info: config2.json successfully parsed and validated")
        assertNextLine("Initialization Info: config3.json successfully parsed and validated")
        // The Simulation starts with tick 0
        assertNextLine("Simulation starts")
        assertNextLine("Simulation Tick: 0")
        assertNextLine("Simulation Tick: 1")

        assertNextLine("Simulation Tick: 2")
        // em0 is assigned to police station (id 0)
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // pc0, pc1, pc2, pc3, and dogCar8 are allocated to em0, need 1 tick
        assertNextLine("Asset Allocation: 0 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 2 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 8 allocated to 0; 1 ticks to arrive.")
        // em0 sends request for ambulance to hospital (id 1)
        assertNextLine("Asset Request: 1 sent to 1 for 0.")
        // amb10 is allocated to em0, needs 2 ticks
        assertNextLine("Asset Allocation: 10 allocated to 0; 2 ticks to arrive.")

        assertNextLine("Simulation Tick: 3")
        // em1 is assigned to police station (id 1)
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        // pc4, pc5, pc6, and pc7 are allocated to em1, need 2 ticks
        assertNextLine("Asset Allocation: 4 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 5 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 6 allocated to 1; 2 ticks to arrive.")
        assertNextLine("Asset Allocation: 7 allocated to 1; 2 ticks to arrive.")
        // em1 sends request for ambulance to hospital (id 1)
        assertNextLine("Asset Request: 2 sent to 1 for 1.")
        // amb11 is allocated to em1, needs 1 tick
        assertNextLine("Asset Allocation: 11 allocated to 1; 1 ticks to arrive.")
        // pc0, pc1, pc2, pc3, and dogCar8 arrived at em0
        assertNextLine("Asset Arrival: 0 arrived at 2.")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Asset Arrival: 2 arrived at 2.")
        assertNextLine("Asset Arrival: 3 arrived at 2.")
        assertNextLine("Asset Arrival: 8 arrived at 2.")

        assertNextLine("Simulation Tick: 4")
        // amb10 arrived at em0
        assertNextLine("Asset Arrival: 10 arrived at 3.")
        // amb11 arrived at em1
        assertNextLine("Asset Arrival: 11 arrived at 4.")
        // em0 can start handling since all assets arrived
        assertNextLine("Emergency Handling Start: 0 handling started.")

        assertNextLine("Simulation Tick: 5")
        // pc4, pc5, pc6, and pc7 arrived at em1
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        assertNextLine("Asset Arrival: 5 arrived at 3.")
        assertNextLine("Asset Arrival: 6 arrived at 3.")
        assertNextLine("Asset Arrival: 7 arrived at 3.")

        assertNextLine("Simulation Tick: 6")
        // after two ticks of handling, em0 is resolved
        assertNextLine("Emergency Resolved: 0 resolved.")

        assertNextLine("Simulation Tick: 7")
        // reallocated dogCar8 to em1 since em0 is resolved now
        assertNextLine("Asset Reallocation: 8 reallocated to 1.")
        // pc0, pc1, pc2, pc3, returned to police station
        assertNextLine("Asset Arrival: 0 arrived at 1.")
        assertNextLine("Asset Arrival: 1 arrived at 1.")
        assertNextLine("Asset Arrival: 2 arrived at 1.")
        assertNextLine("Asset Arrival: 3 arrived at 1.")

        assertNextLine("Simulation Tick: 8")
        // dogCar8 arrived at em1
        assertNextLine("Asset Arrival: 8 arrived at 3.")
        // amb10 returned to hospital
        assertNextLine("Asset Arrival: 10 arrived at 5.")
        // em1 can start handling since all assets arrived
        assertNextLine("Emergency Handling Start: 1 handling started.")

        assertNextLine("Simulation Tick: 9")
        assertNextLine("Simulation Tick: 10")
        // after two ticks of handling, em1 is resolved
        assertNextLine("Emergency Resolved: 1 resolved.")
        assertNextLine("Simulation End")
        assertNextLine("Simulation Statistics: 0 assets rerouted.")
        assertNextLine("Simulation Statistics: 2 received emergencies.")
        assertNextLine("Simulation Statistics: 0 ongoing emergencies.")
        continueWith()
    }

    private suspend fun continueWith() {
        assertNextLine("Simulation Statistics: 0 failed emergencies.")
        assertNextLine("Simulation Statistics: 2 resolved emergencies.")
        assertEnd()
    }
}
