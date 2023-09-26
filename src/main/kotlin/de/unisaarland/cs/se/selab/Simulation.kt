package de.unisaarland.cs.se.selab

import java.io.File

object Simulation {

    var currentTick: Int = 0
    val maximumTicks: Int? = null
    val emergencies: MutableList<Emergency> = mutableListOf()
    val events: MutableList<Event> = mutableListOf()
    val map = GraphMap()
    val statistics = Statistics()

    //initializes the setting and returns whether the process was successful
    fun initialize(
        mapConfig: File, baseVehicleConfig: File,
        emergEventConfig: File
    ): Boolean {
        val mapParser: MapParser = MapParser(map, mapConfig)
        val mapParsed: Boolean = mapParser.parseMap()
        Logger.logInitInfo(mapConfig.name, mapParsed)
        if (!mapParsed) {
            return false
        }
        val jsonParser: JsonParser = JsonParser(map, baseVehicleConfig, emergEventConfig)
        val vehiclesParsed: Boolean = jsonParser.parseVehicles()
        val basesParsed: Boolean = jsonParser.parseBases()
        Logger.logInitInfo(baseVehicleConfig.name, vehiclesParsed && basesParsed)
        if (!(vehiclesParsed && basesParsed)) {
            return false
        }
        val eventsParsed: Boolean = jsonParser.parseEvents()
        val emergenciesParsed: Boolean = jsonParser.parseEmergency()
        Logger.logInitInfo(emergEventConfig.name, eventsParsed && emergenciesParsed)
        if (!(eventsParsed && emergenciesParsed)) {
            return false
        }
        return true


    }

    private fun increaseCurrentTick() {
        currentTick += 1
    }

    //adds an emergency to the global event list
    fun addEmergency(em: Emergency) {
        emergencies.add(em)
    }

    //adds an event to the global event list
    fun addEvent(ev: Event) {
        events.add(ev)
    }

    private fun simulateEmergencyPhase() {
        for (em in emergencies) {
            if (em.getTick() == currentTick)
                EMCC.addStartingEmergency(em)
        }
        EMCC.notifyObservers()
    }

    private fun simulatePlanningPhase() {
        EMCC.orderEmergencies()
        EMCC.allocateAssets()
    }

    private fun simulateRequestPhase() {
        EMCC.processRequests()
    }

    private fun simulateUpdatePhase() {
        //   EMCC.updateAssets()
        //   EMCC.updateEmergencies()
        val checkEventsChange: Boolean = EMCC.updateEvents()
        if (checkEventsChange) {
            EMCC.rerouteVehicles()
        }
        increaseCurrentTick()
    }

    private fun finalEvaluation() {
        val rerouted: Int = statistics.getRerouted()
        val receivedEms: Int = statistics.getReceived()
        val ongoingEms: Int = statistics.getOngoing()
        val failedEms: Int = statistics.getFailed()
        val resolvedEms: Int = statistics.getResolved()
        Logger.logSimulationEnd()
        Logger.logStatistics(rerouted, receivedEms, ongoingEms, failedEms, resolvedEms)
    }

    private fun simulateTick() {
        simulateEmergencyPhase()
        simulatePlanningPhase()
        simulateRequestPhase()
        simulateUpdatePhase()
    }

    fun simulateSimulation() {
        if (maximumTicks == null) {
            while (true) {
                simulateTick()
            }
        } else {
            while (currentTick <= maximumTicks) {
                simulateTick()
            }
        }
        finalEvaluation()
    }
}

