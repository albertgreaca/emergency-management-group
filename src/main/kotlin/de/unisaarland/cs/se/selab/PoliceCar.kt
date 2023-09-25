package de.unisaarland.cs.se.selab

class PoliceCar(
    private val id: Int,
    private val base: Base,
    private val staffCapacity: Int,
    private val vehicleHeight: Int,
    private var position: Position? = null,
    private val criminalCapacity: Int,
    private var transportedCriminals: Int
) : Vehicle(
    id,
    VehicleType.POLICE_CAR,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {
    fun getCriminalCapacity(): Int {
        return criminalCapacity
    }

    fun getTransportedCriminals(): Int {
        return transportedCriminals
    }
    fun setTransportedCriminals(criminals: Int) {
        transportedCriminals = criminals
    }



}
