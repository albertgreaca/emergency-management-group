package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTest

class LessDoctorsThanDoctorCars : SystemTest() {
    override val name = "LessDoctorsThanDoctorCars"

    override val map = "testcases/lessDoctorsThanDoctorCars/map.dot"
    override val assets = "testcases/lessDoctorsThanDoctorCars/config2.json"
    override val scenario = "testcases/lessDoctorsThanDoctorCars/config3.json"
    override val maxTicks = 50

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
        // em0 is assigned to hospital (id 0)
        assertNextLine("Emergency Assignment: 0 assigned to 0")
        // amb0, amb1, and docCar4 allocated to em0, 1 tick needed
        assertNextLine("Asset Allocation: 0 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 1 allocated to 0; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 4 allocated to 0; 1 ticks to arrive.")

        assertNextLine("Simulation Tick: 3")
        // em1 is assigned to hospital (id 0)
        assertNextLine("Emergency Assignment: 1 assigned to 0")
        // amb2 and amb3 allocated to em1, 1 tick needed
        assertNextLine("Asset Allocation: 2 allocated to 1; 1 ticks to arrive.")
        assertNextLine("Asset Allocation: 3 allocated to 1; 1 ticks to arrive.")
        // amb0, amb1, and docCar4 arrived at em0
        assertNextLine("Asset Arrival: 0 arrived at 2.")
        assertNextLine("Asset Arrival: 1 arrived at 2.")
        assertNextLine("Asset Arrival: 4 arrived at 2.")
        // em0 can start handling since all assets arrived
        assertNextLine("Emergency Handling Start: 0 handling started.")

        assertNextLine("Simulation Tick: 4")
        // amb2 and amb3 arrived at em1
        assertNextLine("Asset Arrival: 2 arrived at 3.")
        assertNextLine("Asset Arrival: 3 arrived at 3.")

        assertNextLine("Simulation Tick: 5")
        // after two ticks of handling, em0 is resolved
        assertNextLine("Emergency Resolved: 0 resolved.")

        assertNextLine("Simulation Tick: 6")
        // reallocated docCar4 to em1 since em0 is resolved now
        assertNextLine("Asset Reallocation: 4 reallocated to 1.")
        // amb0 and amb1 returned to hospital
        assertNextLine("Asset Arrival: 0 arrived at 1.")
        assertNextLine("Asset Arrival: 1 arrived at 1.")
        // docCar4 arrived at em1
        assertNextLine("Asset Arrival: 4 arrived at 3.")
        // em1 can start handling since all assets arrived
        assertNextLine("Emergency Handling Start: 1 handling started.")

        assertNextLine("Simulation Tick: 7")

        assertNextLine("Simulation Tick: 8")

        assertNextLine("Simulation Tick: 9")
        // after three ticks of handling, em1 is resolved
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
