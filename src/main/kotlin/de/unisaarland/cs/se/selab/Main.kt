package de.unisaarland.cs.se.selab

import java.io.File
import java.io.FileOutputStream

/**
 * This is the entry point of the simulation.
 */
fun main(args: Array<String>) {
    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val map = argsMap["--map"] ?: return
    val assets = argsMap["--assets"] ?: return
    val scenario = argsMap["--scenario"] ?: return
    val ticks = argsMap["--ticks"]?.toIntOrNull()
    val out = argsMap["--out"]
    if (out != null) {
        Logger.setPrintWriter(FileOutputStream(out))
    } else {
        Logger.setPrintWriter(System.out)
    }
    val f1 = File(map)
    val f2 = File(assets)
    val f3 = File(scenario)
    Simulation.maximumTicks = ticks
    if (!Simulation.initialize(f1,f2,f3)) {
        return
    }
    Simulation.simulateSimulation()
}
