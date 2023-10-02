package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.mainlogic.Simulation
import de.unisaarland.cs.se.selab.utils.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 * This is the entry point of the simulation.
 */
fun main(args: Array<String>) {
    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val map: String
    val assets: String
    val scenario: String
    try {
        map = requireNotNull(argsMap["--map"] ?: argsMap["-m"])
        assets = requireNotNull(argsMap["--assets"] ?: argsMap["-a"])
        scenario = requireNotNull(argsMap["--scenario"] ?: argsMap["-s"])
    } catch (E: IllegalArgumentException) {
        E.message
        return
    }

    val ticks = argsMap["--ticks"] ?: argsMap["-t"]
    val maxticks = ticks?.toIntOrNull()
    if (maxticks != null) {
        if (maxticks < 0) {
            return
        }
    }
    val out = argsMap["--out"] ?: argsMap["-o"]
    if (out != null) {
        Logger.pw = PrintWriter(FileOutputStream(out))
    } else {
        Logger.pw = PrintWriter(System.out)
    }
    try {
        val f1 = File(map)
        val f2 = File(assets)
        val f3 = File(scenario)
        Simulation.maximumTicks = maxticks
        if (!Simulation.initialize(f1, f2, f3)) {
            Logger.pw.flush()
            Logger.pw.close()
            return
        }
        Simulation.simulateSimulation()
        Logger.pw.flush()
        Logger.pw.close()
    } catch (E: FileNotFoundException) {
        E.message
        return
    }
}
