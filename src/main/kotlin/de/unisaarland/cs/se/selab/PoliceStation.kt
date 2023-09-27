package de.unisaarland.cs.se.selab

/**
 * base for the police department
 */
class PoliceStation(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>) : Base(
    id,
    staff,
    location,
    vehicles
) {
    var dogs: Int = 0
}
