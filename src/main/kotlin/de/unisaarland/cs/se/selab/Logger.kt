import java.io.PrintWriter

object Logger {

    //TODO(how do we create the output file?)
    private val pw: PrintWriter = PrintWriter("")

    fun logInitInfo(filename: String, isValid: Boolean) {
        if (isValid)
            pw.print("$filename successfully parsed and validated")
        else
            pw.print("$filename invalid")
    }

    fun logSimulationStart() {
        pw.print("Simulation starts")
    }

    fun logSimulationTick(t: Int) {
        pw.print("Simulation Tick: $t")
    }

    fun logEmergencyAssignment(eid: Int, bid: Int) {
        pw.print("Emergency Assignment: $eid assigned to $bid")
    }

    fun logAssetAllocation(aid: Int, eid: Int, t: Int) {
        pw.print("Asset Allocation: $aid allocated to $eid; $t ticks to arrive.")
    }

    fun logAssetReallocation(aid: Int, eid: Int) {
        pw.print("Asset Reallocation: $aid reallocated to $eid.")
    }

    fun logAssetRequest(rid: Int, bid: Int, eid:Int) {
        pw.print("Asset Request: $rid sent to $bid for $eid.")
    }

    fun logRequestFailed(eid: Int) {
        pw.print("Request Failed: $eid failed.")
    }

    fun logAssetArrival(aid: Int, vid: Int) {
        pw.print("Asset Arrival: $aid arrived at $vid.")
    }

    fun logEmergencyHandlingStart(eid: Int) {
        pw.print("Emergency Handling Start: $eid handling started.")
    }

    fun logEmergencyResolved(eid: Int) {
        pw.print("Emergency Resolved: $eid resolved.")
    }

    fun logEmergencyFailed(eid: Int) {
        pw.print("Emergency Failed: $eid failed.")
    }

    fun logEventEnded(evid: Int) {
        pw.print("Event Ended: $evid ended.")
    }

    fun logEventTriggered(evid: Int) {
        pw.print("Event Triggered: $evid triggered.")
    }

    fun logAssetsRerouted(numOfAssets: Int) {
        pw.print("Assets Rerouted: $numOfAssets")
    }

    fun logSimulationEnd() {
        pw.print("Simulation End")
    }

    fun logStatistics(received: Int, ongoing: Int, failed: Int, resolved: Int) {
        pw.print("Simulation Statistics: $received received emergencies.")
        pw.print("Simulation Statistics: $ongoing ongoing emergencies.")
        pw.print("Simulation Statistics: $failed failed emergencies.")
        pw.print("Simulation Statistics: $resolved resolved emergencies.")
    }
}