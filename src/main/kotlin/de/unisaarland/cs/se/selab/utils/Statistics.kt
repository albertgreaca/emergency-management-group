package de.unisaarland.cs.se.selab.utils

/**
 * this class contains all statistics of the simulation
 */
data class Statistics(
    var receivedEmergencies: Int = 0,
    var ongoingEmergencies: Int = 0,
    var failedEmergencies: Int = 0,
    var resolvedEmergencies: Int = 0,
    var reroutedAssets: Int = 0
)
