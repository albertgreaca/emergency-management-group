package de.unisaarland.cs.se.selab

import java.io.File

class Simulation(maxTick: Int) {

    private var currentTick: Int = 0
    private val maximumTicks: Int = maxTick
    private val emergencies: MutableList<Emergency> = mutableListOf()
    private val events: MutableList<Event> = mutableListOf()
    private val map = GraphMap()
    private val statistics = Statistics()

    fun initialize(
        mapConfig: File, baseVehicleConfig: File,
        emergEventConfig: File
    ) {
        val mapParser: MapParser = MapParser(map, mapConfig)
        val mapParsed: Boolean = mapParser.parseMap()
        Logger.logInitInfo(mapConfig.name, mapParsed)

    }

    fun getCurrentTick(): Int {
        return currentTick
    }

    fun increaseCurrentTick() {
        currentTick += 1
    }

    fun getMaxTicks(): Int {
        return maximumTicks
    }

    fun addEmergency(em: Emergency) {
        emergencies.add(em)
    }

    fun addEvent(ev: Event) {
        events.add(ev)
    }

    fun getMap(): GraphMap {
        return map
    }

    // add starting emergencies to EMCC and notify its observers
    fun simulateEmergencyPhase() {
        for (em in emergencies) {
            if (em.getTick() == currentTick)
                EMCC.addStartingEmergency(em)
        }
        EMCC.notifyObservers()
    }

    // order emergencies by severity then id, allocate assets for each one
    fun simulatePlanningPhase() {
        EMCC.orderEmergencies()
        EMCC.allocateAssets()
    }

    fun simulateRequestPhase() {
        EMCC.processRequests()
    }

    fun simulateUpdatePhase() {
     //   EMCC.updateAssets()
     //   EMCC.updateEmergencies()
        val checkEventsChange: Boolean = EMCC.updateEvents()
        if (checkEventsChange) {
            EMCC.rerouteVehicles()
        }
        increaseCurrentTick()
    }

    fun finalEvaluation() {
        val rerouted: Int = statistics.getRerouted()
        val receivedEms: Int = statistics.getReceived()
        val ongoingEms: Int = statistics.getOngoing()
        val failedEms: Int = statistics.getFailed()
        val resolvedEms: Int = statistics.getResolved()
        Logger.logSimulationEnd()
        Logger.logStatistics(rerouted, receivedEms, ongoingEms, failedEms, resolvedEms)
    }

    fun simulateTick() {
        simulateEmergencyPhase()
        simulatePlanningPhase()
        simulateRequestPhase()
        simulateUpdatePhase()
        finalEvaluation()
    }
}
