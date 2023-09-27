package de.unisaarland.cs.se.selab

/**
 * base for the police department
 */
class PoliceStation(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>, var dogs: Int) : Base(
    id,
    staff,
    location,
    vehicles
)
