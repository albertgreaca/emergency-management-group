package de.unisaarland.cs.se.selab

/**
 * this class contains all statistics of the simulation
 */
class Statistics(
    var receivedEmergencies: Int = 0,
    var ongoingEmergencies: Int = 0,
    var failedEmergencies: Int = 0,
    var resolvedEmergencies: Int = 0,
    var reroutedAssets: Int = 0
) {

    /**
     *
     */
    fun increaseReceived() {
        receivedEmergencies++
    }

    /**
     *
     */
    fun increaseOngoing() {
        ongoingEmergencies++
    }

    /**
     *
     */
    fun decreaseOngoing() {
        ongoingEmergencies--
    }

    /**
     *
     */
    fun increaseFailed() {
        failedEmergencies++
    }

    /**
     *
     */
    fun increaseResolved() {
        resolvedEmergencies++
    }

    /**
     *
     */
    fun increaseRerouted(n: Int) {
        reroutedAssets += n
    }
}
