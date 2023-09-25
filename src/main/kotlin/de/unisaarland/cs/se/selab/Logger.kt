package de.unisaarland.cs.se.selab

import java.io.OutputStream
import java.io.PrintWriter

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
    fun logInitInfo(filename: String, isValid: Boolean) {
        if (isValid) {
            pw.print("Initialization Info: $filename successfully parsed and validated")
        } else {
            pw.print("Initialization Info: $filename invalid")
        }
    }

    /**
     *
     */
    fun logSimulationStart() {
        pw.print("Simulation starts")
    }

    /**
     *
     */
    fun logSimulationTick(t: Int) {
        pw.print("Simulation Tick: $t")
    }

    /**
     *
     */
    fun logEmergencyAssignment(eid: Int, bid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Emergency Assignment: $eid assigned to $bid")
    }

    /**
     *
     */
    fun logAssetAllocation(aid: Int, eid: Int, t: Int) {
        pw.print("Asset Allocation: $aid allocated to $eid; $t ticks to arrive.")
    }

    /**
     *
     */
    fun logAssetReallocation(aid: Int, eid: Int) {
        pw.print("Asset Reallocation: $aid reallocated to $eid.")
    }

    /**
     *
     */
    fun logAssetRequest(rid: Int, bid: Int, eid: Int) {
        pw.print("Asset de.unisaarland.cs.se.selab.Request: $rid sent to $bid for $eid.")
    }

    /**
     *
     */
    fun logRequestFailed(eid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Request Failed: $eid failed.")
    }

    /**
     *
     */
    fun logAssetArrival(aid: Int, vid: Int) {
        pw.print("Asset Arrival: $aid arrived at $vid.")
    }

    /**
     *
     */
    fun logEmergencyHandlingStart(eid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Emergency Handling Start: $eid handling started.")
    }

    /**
     *
     */
    fun logEmergencyResolved(eid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Emergency Resolved: $eid resolved.")
    }

    /**
     *
     */
    fun logEmergencyFailed(eid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Emergency Failed: $eid failed.")
    }

    /**
     *
     */
    fun logEventEnded(evid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Event Ended: $evid ended.")
    }

    /**
     *
     */
    fun logEventTriggered(evid: Int) {
        pw.print("de.unisaarland.cs.se.selab.Event Triggered: $evid triggered.")
    }

    /**
     *
     */
    fun logAssetsRerouted(numOfAssets: Int) {
        pw.print("Assets Rerouted: $numOfAssets")
    }

    /**
     *
     */
    fun logSimulationEnd() {
        pw.print("Simulation End")
    }

    /**
     *
     */
    fun logStatistics(rerouted: Int, received: Int, ongoing: Int, failed: Int, resolved: Int) {
        pw.print("Simulation Statistics: $rerouted assets rerouted.")
        pw.print("Simulation Statistics: $received received emergencies.")
        pw.print("Simulation Statistics: $ongoing ongoing emergencies.")
        pw.print("Simulation Statistics: $failed failed emergencies.")
        pw.print("Simulation Statistics: $resolved resolved emergencies.")
    }
}
