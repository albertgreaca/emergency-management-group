package de.unisaarland.cs.se.selab

import java.io.OutputStream
import java.io.PrintWriter

/**
 * Responsible for all possible logs
 */
object Logger {

    private var pw: PrintWriter = PrintWriter(System.out)

    /**
     *
     */
    fun setPrintWriter(o: OutputStream) {
        pw = PrintWriter(o)
    }

    /**
     *
     */
    fun logInitInfo(filename: String, isValid: Boolean): String {
        var output = ""
        if (isValid) {
            output = "Initialization Info: $filename successfully parsed and validated"
            pw.print(output)
        } else {
            output = "Initialization Info: $filename invalid"
            pw.print(output)
        }
        return output
    }

    /**
     *
     */
    fun logSimulationStart(): String {
        val output = "Simulation starts"
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logSimulationTick(t: Int): String {
        val output = "Simulation Tick: $t"
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEmergencyAssignment(eid: Int, bid: Int): String {
        val output = "Emergency Assignment: $eid assigned to $bid"
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logAssetAllocation(aid: Int, eid: Int, t: Int): String {
        val output = "Asset Allocation: $aid allocated to $eid; $t ticks to arrive."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logAssetReallocation(aid: Int, eid: Int): String {
        val output = "Asset Reallocation: $aid reallocated to $eid."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logAssetRequest(rid: Int, bid: Int, eid: Int): String {
        val output = "Asset Request: $rid sent to $bid for $eid."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logRequestFailed(eid: Int): String {
        val output = "Request Failed: $eid failed."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logAssetArrival(aid: Int, vid: Int): String {
        val output = "Asset Arrival: $aid arrived at $vid."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEmergencyHandlingStart(eid: Int): String {
        val output = "Emergency Handling Start: $eid handling started."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEmergencyResolved(eid: Int): String {
        val output = "Emergency Resolved: $eid resolved."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEmergencyFailed(eid: Int): String {
        val output = "Emergency Failed: $eid failed."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEventEnded(evid: Int): String {
        val output = "Event Ended: $evid ended."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logEventTriggered(evid: Int): String {
        val output = "Event Triggered: $evid triggered."
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logAssetsRerouted(numOfAssets: Int): String {
        val output = "Assets Rerouted: $numOfAssets"
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logSimulationEnd(): String {
        val output = "Simulation End"
        pw.print(output)
        return output
    }

    /**
     *
     */
    fun logStatistics(rerouted: Int, received: Int, ongoing: Int, failed: Int, resolved: Int): String {
        val output = "Simulation Statistics: $rerouted assets rerouted.\nSimulation Statistics: $received received " +
                "emergencies.\nSimulation Statistics: $ongoing ongoing emergencies.\nSimulation Statistics: $failed " +
                "failed emergencies.\nSimulation Statistics: $resolved resolved emergencies."
        pw.print(output)
        return output
    }
}
