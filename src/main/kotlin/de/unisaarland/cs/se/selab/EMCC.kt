package de.unisaarland.cs.se.selab

/**
 * This is the class responsible for simulating the different phases of a tick
 */
object EMCC {
    val observers: MutableList<EmergencyObserver> = mutableListOf()
    val startingEmergencies: MutableList<Emergency> = mutableListOf()
    val handledEmergencies: MutableList<Emergency> = mutableListOf()
    val activeEvents: MutableList<Event> = mutableListOf()
    val startingEvents: MutableList<Event> = mutableListOf()
    var nextRequestId: Int = 1
    val requests: MutableList<Request> = mutableListOf()

    /**
     * increases the ID of the next request by 1
     */
    fun increaseNextRequestId() {
        nextRequestId++
    }

    /**
     * notifies all observers about new emergencies, this initiates the emergency phase
     */
    fun notifyObservers() {
        for (o in observers) {
            o.update(startingEmergencies)
        }
    }

    /**
     * adds a department to the list of observers
     */
    fun addObserver(ob: EmergencyObserver) {
        observers.add(ob)
    }

    /**
     * adds an emergency to the startingEmergencies list
     */
    fun addStartingEmergency(em: Emergency) {
        startingEmergencies.add(em)
    }

    /**
     * orders the starting emergencies by severity, then by ID
     */
    fun orderEmergencies() {
        startingEmergencies.sortWith(compareBy({ it.getSeverity() }, { it.getId() }))
    }

    /**
     * allocates assets for each starting emergency
     */
    fun allocateAssets() {
        // iterate over emergencies
        for (em in startingEmergencies) {
            // get the base assigned to the emergency
            var emBase = em.getBase()!!

            // base tries to allocate resources for emergency, returns what is left
            var resourcesAfterAllocating = emBase.requestResources(em)

            // update the resources in the emergency
            em.getResources().updateDifference(resourcesAfterAllocating)

            // base tries to reallocate resources from other emergencies, returns what is left
            var resourcesAfterReallocating = emBase.reallocateResources(em)

            // update the resources in the emergency
            em.getResources().updateDifference(resourcesAfterReallocating)

            // if there are remaining resources after reallocating, a request to the next base has to be created
            if (!resourcesAfterReallocating.isEmpty()) {
                TODO("implement creating requests")
            }
        }
    }

    /**
     * handles all requests made in the allocation phase of the current tick
     */
    fun processRequests() {
        TODO("not implemented yet")
    }

    /**
     * adds an event to the startingEvents list
     */
    fun addStartingEvent(event: Event) {
        startingEvents.add(event)
    }

    /**
     * removes an event from the startingEvents list
     */
    fun removeStartingEvent(event: Event) {
        startingEvents.remove(event)
    }

    /**
     * adds an event to the activeEvents list
     */
    fun addActiveEvent(event: Event) {
        activeEvents.add(event)
    }

    /**
     * removes an event from the activeEvents list
     */
    fun removeActiveEvent(event: Event) {
        activeEvents.remove(event)
    }

    /**
     * moves an event from the startingEvents list to the activeEvents list
     */
    fun moveFromStartingToActive(event: Event) {
        removeStartingEvent(event)
        addActiveEvent(event)
    }

    /**
     * moves an event from the activeEvents list to the startingEvents list
     */
    fun moveFromActiveToStarting(event: Event) {
        removeActiveEvent(event)
        addStartingEvent(event)
    }

    /**
     * moves all driving assets
     */
    fun updateAssets() {
        for (em in Simulation.emergencies) {
            for (vec in em.assignedVehicles) {
                if (vec.position != null && vec.position!!.arrivalTicks != 0)
                    vec.move()
            }
        }
    }

    /**
     * updates the state of all emergencies
     */
    fun updateEmergencies() {
        // update all emergencies whose handling started (neededResources are empty and all assets have arrival time of 0)
        // update all emergencies that were resolved ()
        // update all emergencies that failed (tick + maxDuration == Simulation.currentTick)
        TODO()
    }

    /**
     * updates starting and ending events
     */
    fun updateEvents(): Boolean {
        var eventsChanged = false
        // first handle the ending events
        for (event in activeEvents) {
            if (event.tick + event.duration == Simulation.currentTick) {
                removeActiveEvent(event)
                event.stopEvent()
                eventsChanged = true
            }
        }
        // then handle the starting events
        for (event in startingEvents) {
            if (event.executeStart())
                eventsChanged = true
        }
        return eventsChanged
    }

    /**
     * reroutes all vehicles that are currently driving
     */
    fun rerouteVehicles() {
        var numberOfReroutedVehicles = 0
        for (em in Simulation.emergencies) {
            for (vec in em.assignedVehicles) {
                if (vec.reroutable()) {
                    val wasRerouted = vec.reroute()
                    numberOfReroutedVehicles = if (wasRerouted) numberOfReroutedVehicles + 1 else numberOfReroutedVehicles
                }
            }
        }
        Logger.logAssetsRerouted(numberOfReroutedVehicles)
        Simulation.statistics.increaseRerouted(numberOfReroutedVehicles)
    }
}
