package de.unisaarland.cs.se.selab.mainlogic

import de.unisaarland.cs.se.selab.emergencies.Emergency
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.graphlogic.GraphMap
import de.unisaarland.cs.se.selab.parser.JsonParser
import de.unisaarland.cs.se.selab.parser.MapParser
import de.unisaarland.cs.se.selab.utils.Logger
import de.unisaarland.cs.se.selab.utils.Statistics
import org.json.JSONException
import java.io.File

/**
 * Class representing the main structure of the simulation
 */
object Simulation {

    var currentTick: Int = 0
    var maximumTicks: Int? = null
    val emergencies: MutableList<Emergency> = mutableListOf()
    val events: MutableList<Event> = mutableListOf()
    var map = GraphMap()
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
        if (!mapParsed) {
            return false
        }
        val jsonParser = JsonParser(map, baseVehicleConfig, emergEventConfig)
        val basesParsed: Boolean
        val vehiclesParsed: Boolean
        try {
            basesParsed = jsonParser.parseBases()
            vehiclesParsed =
                jsonParser.parseVehicles() // initialize vehicles and bases and check for validity
            Logger.logInitInfo(baseVehicleConfig.name, vehiclesParsed && basesParsed)
        } catch (e: JSONException) {
            e.message
            Logger.logInitInfo(baseVehicleConfig.name, false)
            return false
        }

        try {
            if (vehiclesParsed && basesParsed) {
                val eventsParsed: Boolean =
                    jsonParser.parseEvents() // initialize events and emergencies and check for validity
                val emergenciesParsed: Boolean = jsonParser.parseEmergency()
                Logger.logInitInfo(emergEventConfig.name, eventsParsed && emergenciesParsed)
                if (eventsParsed && emergenciesParsed) {
                    return true
                }
            }
        } catch (e: JSONException) {
            e.message
            Logger.logInitInfo(emergEventConfig.name, false)
            return false
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
        EMCC.policeDepartment?.updateVehicles()
        EMCC.fireDepartment?.updateVehicles()
        EMCC.ambulanceDepartment?.updateVehicles()
        for (ev in events) {
            if (ev.tick == currentTick && !ev.postponed) {
                EMCC.startingEvents.add(ev)
            }
        }
        val checkEventsChange: Boolean = EMCC.updateEvents()
        if (checkEventsChange) {
            EMCC.rerouteVehicles()
            EMCC.updatenextBases()
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
        Logger.logSimulationTick(currentTick)
        simulateEmergencyPhase()
        simulatePlanningPhase()
        simulateRequestPhase()
        simulateUpdatePhase()
    }

    /** perform the entire simulation
     */
    fun simulateSimulation() {
        Logger.logSimulationStart()
        EMCC.updatenextBases()
        if (maximumTicks == null) {
            while (!emergenciesdone()) {
                simulateTick()
            }
        } else {
            while (currentTick < requireNotNull(maximumTicks) && !emergenciesdone()) {
                simulateTick()
            }
        }
        finalEvaluation()
    }

    private fun emergenciesdone(): Boolean {
        return statistics.failedEmergencies + statistics.resolvedEmergencies == emergencies.size
    }
}
