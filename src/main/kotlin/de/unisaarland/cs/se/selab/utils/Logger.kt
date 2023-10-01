package de.unisaarland.cs.se.selab.utils

import java.io.PrintWriter

/**
 * Responsible for all possible logs
 */
object Logger {

    var pw: PrintWriter = PrintWriter(System.out)

    /**
     *
     */
    fun logInitInfo(filename: String, isValid: Boolean): String {
        val output: String
        if (isValid) {
            output = "Initialization Info: $filename successfully parsed and validated"
            pw.println(output)
            pw.flush()
        } else {
            output = "Initialization Info: $filename invalid"
            pw.println(output)
            pw.flush()
        }
        return output
    }

    /**
     *
     */
    fun logSimulationStart(): String {
        val output = "Simulation starts"
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logSimulationTick(t: Int): String {
        val output = "Simulation Tick: $t"
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEmergencyAssignment(eid: Int, bid: Int): String {
        val output = "Emergency Assignment: $eid assigned to $bid"
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logAssetAllocation(aid: Int, eid: Int, t: Int): String {
        val output = "Asset Allocation: $aid allocated to $eid; $t ticks to arrive."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logAssetReallocation(aid: Int, eid: Int): String {
        val output = "Asset Reallocation: $aid reallocated to $eid."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logAssetRequest(rid: Int, bid: Int, eid: Int): String {
        val output = "Asset Request: $rid sent to $bid for $eid."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logRequestFailed(eid: Int): String {
        val output = "Request Failed: $eid failed."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logAssetArrival(aid: Int, vid: Int): String {
        val output = "Asset Arrival: $aid arrived at $vid."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEmergencyHandlingStart(eid: Int): String {
        val output = "Emergency Handling Start: $eid handling started."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEmergencyResolved(eid: Int): String {
        val output = "Emergency Resolved: $eid resolved."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEmergencyFailed(eid: Int): String {
        val output = "Emergency Failed: $eid failed."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEventEnded(evid: Int): String {
        val output = "Event Ended: $evid ended."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logEventTriggered(evid: Int): String {
        val output = "Event Triggered: $evid triggered."
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logAssetsRerouted(numOfAssets: Int): String {
        var output = ""
        if (numOfAssets != 0) {
            output = "Assets Rerouted: $numOfAssets"
            pw.println(output)
            pw.flush()
        }
        return output
    }

    /**
     *
     */
    fun logSimulationEnd(): String {
        val output = "Simulation End"
        pw.println(output)
        pw.flush()
        return output
    }

    /**
     *
     */
    fun logStatistics(rerouted: Int, received: Int, ongoing: Int, failed: Int, resolved: Int): String {
        val output = "Simulation Statistics: $rerouted assets rerouted.\n" +
            "Simulation Statistics: $received received emergencies.\n" +
            "Simulation Statistics: $ongoing ongoing emergencies.\n" +
            "Simulation Statistics: $failed failed emergencies.\n" +
            "Simulation Statistics: $resolved resolved emergencies."
        pw.println(output)
        pw.flush()
        return output
    }
}
