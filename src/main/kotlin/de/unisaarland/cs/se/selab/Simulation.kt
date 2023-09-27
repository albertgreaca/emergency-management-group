package de.unisaarland.cs.se.selab

import java.io.File

/**
 * Class representing the main structure of the simulation
 */
object Simulation {

    var currentTick: Int = 0
    var maximumTicks: Int? = null
    val emergencies: MutableList<Emergency> = mutableListOf()
    val events: MutableList<Event> = mutableListOf()
    val map = GraphMap()
    val statistics = Statistics()

    /** initializes the simulation setting and returns whether the process was successful
     */
    fun initialize(
        mapConfig: File,
        baseVehicleConfig: File,
        emergEventConfig: File
    ): Boolean {
        val mapParser = MapParser(map, mapConfig) // initialize the map and check for validity
        val mapParsed: Boolean = mapParser.parseMap()
        Logger.logInitInfo(mapConfig.name, mapParsed)
        if (mapParsed) {
            val jsonParser = JsonParser(map, baseVehicleConfig, emergEventConfig)
            val vehiclesParsed: Boolean =
                jsonParser.parseVehicles() // initialize vehicles and bases and check for validity
            val basesParsed: Boolean = jsonParser.parseBases()
            Logger.logInitInfo(baseVehicleConfig.name, vehiclesParsed && basesParsed)
            if (vehiclesParsed && basesParsed) {
                val eventsParsed: Boolean =
                    jsonParser.parseEvents() // initialize events and emergencies and check for validity
                val emergenciesParsed: Boolean = jsonParser.parseEmergency()
                Logger.logInitInfo(emergEventConfig.name, eventsParsed && emergenciesParsed)
                if (eventsParsed && emergenciesParsed) {
                    return true
                }
            }
        }
        return false
    }

    /** adds an emergency to the global event list
     */
    fun addEmergency(em: Emergency) {
        emergencies.add(em)
    }

    /** adds an event to the global event list
     */
    fun addEvent(ev: Event) {
        events.add(ev)
    }

    private fun simulateEmergencyPhase() {
        for (em in emergencies) {
            if (em.tick == currentTick) {
                EMCC.addStartingEmergency(em)
            }
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
        EMCC.updateAssets()
        EMCC.updateEmergencies()
        val checkEventsChange: Boolean = EMCC.updateEvents()
        if (checkEventsChange) {
            EMCC.rerouteVehicles()
        }
        currentTick++
    }

    private fun finalEvaluation() {
        val rerouted: Int = statistics.reroutedAssets
        val receivedEms: Int = statistics.receivedEmergencies
        val ongoingEms: Int = statistics.ongoingEmergencies
        val failedEms: Int = statistics.failedEmergencies
        val resolvedEms: Int = statistics.resolvedEmergencies
        Logger.logSimulationEnd()
        Logger.logStatistics(rerouted, receivedEms, ongoingEms, failedEms, resolvedEms)
    }

    private fun simulateTick() {
        simulateEmergencyPhase()
        simulatePlanningPhase()
        simulateRequestPhase()
        simulateUpdatePhase()
    }

    /** perform the entire simulation
     */
    fun simulateSimulation() {
        if (maximumTicks == null) {
            while (true) {
                simulateTick()
            }
        } else {
            while (currentTick <= requireNotNull(maximumTicks)) {
                simulateTick()
            }
        }
        finalEvaluation()
    }
}
