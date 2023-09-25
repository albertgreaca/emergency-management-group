package de.unisaarland.cs.se.selab

/** Class for the Police Car
 */
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
    /**
     * getter for the CriminalCapacity
     * @return CriminalCapacity of the vehicle
     */
    fun getCriminalCapacity(): Int {
        return criminalCapacity
    }

    /**
     * getter for the TransportedCriminals
     * @return TransportedCriminals of the vehicle
     */
    fun getTransportedCriminals(): Int {
        return transportedCriminals
    }

    /**
     * setter for the TrasportedCriminals
     */
    fun setTransportedCriminals(criminals: Int) {
        transportedCriminals = criminals
    }
}
