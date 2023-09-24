package de.unisaarland.cs.se.selab

import java.io.File

/**
 * This is the entry point of the simulation.
 */
fun main(args: Array<String>) {
    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    var map = argsMap["--map"] ?: return
    var assets = argsMap["--assets"] ?: return
    var scenario = argsMap["--scenario"] ?: return
    var ticks = argsMap["--ticks"]?.toIntOrNull()
    var out = argsMap["--out"]
    if(out != null) {
        Logger.setPrintWriter(File(out).outputStream())
    } else {
        Logger.setPrintWriter(System.out)
    }
    var f1 = File(map)
    var f2 = File(assets)
    var f3  = File(scenario)



}
