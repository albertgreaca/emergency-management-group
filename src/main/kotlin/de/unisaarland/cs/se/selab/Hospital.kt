package de.unisaarland.cs.se.selab

/**
 * Hospital Base with number of doctors
 */
class Hospital(id: Int, staff: Int, location: Vertex, vehicles: MutableList<Vehicle>) : Base(
    id,
    staff,
    location,
    vehicles
) {
    var doctors: Int = 0

    /**
     * random function to increase doctors?
     */
    public fun addDoctors(number: Int) {
        this.doctors = doctors + number
    }
}
