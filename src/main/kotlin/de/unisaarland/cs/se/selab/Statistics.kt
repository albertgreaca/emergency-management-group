package de.unisaarland.cs.se.selab

class Statistics(private var receivedEmergencies: Int = 0, private var ongoingEmergencies: Int = 0,
                 private var failedEmergencies: Int = 0, private var resolvedEmergencies: Int = 0,
                 private var reroutedAssets: Int = 0) {

    fun getReceived(): Int {
        return receivedEmergencies
    }

    fun getOngoing(): Int {
        return ongoingEmergencies
    }

    fun getFailed(): Int {
        return failedEmergencies
    }

    fun getResolved(): Int {
        return resolvedEmergencies
    }

    fun getRerouted(): Int {
        return reroutedAssets
    }

    fun increaseReceived() {
        receivedEmergencies++
    }

    fun increaseOngoing() {
        ongoingEmergencies++
    }

    fun decreaseOngoing() {
        ongoingEmergencies--
    }

    fun increaseFailed() {
        failedEmergencies++
    }

    fun increaseResolved() {
        resolvedEmergencies++
    }

    fun increaseRerouted(n: Int) {
        reroutedAssets+=n
    }
}