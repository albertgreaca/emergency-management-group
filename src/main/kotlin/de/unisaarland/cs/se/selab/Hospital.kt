package de.unisaarland.cs.se.selab

/**
 * Hospital Base with number of doctors
 */
class Hospital(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>, var doctors: Int) : Base(
    id,
    staff,
    location,
    vehicles
) {
    /**
     * random function to increase doctors?
     */
    public fun addDoctors(number: Int) {
        this.doctors = doctors + number
    }
}
