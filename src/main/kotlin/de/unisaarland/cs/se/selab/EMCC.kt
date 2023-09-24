package de.unisaarland.cs.se.selab

object EMCC {

    private val observers: MutableList<EmergencyObserver> = mutableListOf()
    private val startingEmergencies: MutableList<Emergency> = mutableListOf()
    private val handledEmergencies: MutableList<Emergency> = mutableListOf()
    private val activeEvents: MutableList<Event> = mutableListOf()
    private val startingEvents: MutableList<Event> = mutableListOf()
    private var nextRequestId: Int = 1
    private val requests: MutableList<Request> = mutableListOf()

    fun getNextRequestId(): Int {
        return nextRequestId
    }

    fun getStartingEmergencies(): MutableList<Emergency> {
        return startingEmergencies
    }

    fun getHandledEmergencies(): MutableList<Emergency> {
        return handledEmergencies
    }

    fun getStartingEvents(): MutableList<Event> {
        return startingEvents
    }

    fun getActiveEvents(): MutableList<Event> {
        return activeEvents
    }

    fun getRequests(): MutableList<Request> {
        return requests
    }

    fun increaseNextRequestId() {
        nextRequestId++
    }

    fun notifyObservers() {
        for (o in observers) {
            o.update(startingEmergencies)
        }
    }

    fun addObserver(ob: EmergencyObserver) {
        observers.add(ob)
    }

    fun addStartingEmergency(em: Emergency) {
        startingEmergencies.add(em)
    }

    fun orderEmergencies() {
        startingEmergencies.sortWith(compareBy({ it.getSeverity() }, { it.getId() }))
    }

    fun allocateAssets() {
        // iterate over emergencies
        for (em in startingEmergencies) {

            // get the base assigned to the emergency
            var emBase = em.getBase()!!

            // base tries to allocate resources for emergency, returns what is left
            var resourcesAfterAllocating  = emBase.requestResources(em)

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

    fun processRequests() {
        // iterate over requests
        for (request in getRequests()) {

        }
        TODO("not implemented yet")
    }

    fun addStartingEvent(event: Event) {
        startingEvents.add(event)
    }

    fun removeStartingEvent(event: Event) {
        startingEvents.remove(event)
    }

    fun addActiveEvent(event: Event) {
        activeEvents.add(event)
    }

    fun removeActiveEvent(event: Event) {
        activeEvents.remove(event)
    }

    fun moveFromStartingToActive(event: Event) {
        removeStartingEvent(event)
        addActiveEvent(event)
    }

    fun moveFromActiveToStarting(event: Event) {
        removeActiveEvent(event)
        addStartingEvent(event)
    }

    fun updateEvents(): Boolean {
        TODO("not implemented")
    }

    fun rerouteVehicles() {
        TODO("not implemented")
    }
}