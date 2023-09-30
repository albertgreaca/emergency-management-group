package de.unisaarland.cs.se.selab.bases

import de.unisaarland.cs.se.selab.graphlogic.Vertex
import de.unisaarland.cs.se.selab.vehicles.Vehicle

/**
 * base for the police department
 */
class PoliceStation(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>, var dogs: Int) : Base(
    id,
    staff,
    location,
    vehicles
)
