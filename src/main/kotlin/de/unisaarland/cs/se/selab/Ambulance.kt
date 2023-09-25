package de.unisaarland.cs.se.selab

/** Class for the Ambulance
 */
class Ambulance(
    private val id: Int,
    private val base: Base,
    private val staffCapacity: Int,
    private val vehicleHeight: Int,
    private var position: Position? = null,
    private var patientOnBoard: Boolean,
) : Vehicle(
    id,
    VehicleType.AMBULANCE,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {

    /**
     * getter for knowing if there is a patient on board
     * @return true if there is a patient on board, else false
     */
    fun getPatientOnBoard(): Boolean {
        return patientOnBoard
    }

    /**
     * setter for the patient on board
     */
    fun setPatientOnBoard(patient: Boolean) {
        patientOnBoard = patient
    }
}
