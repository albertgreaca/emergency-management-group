package de.unisaarland.cs.se.selab

class Ambulance (
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
    fun getPatientOnBoard(): Boolean {
        return patientOnBoard
    }
    fun setPatientOnBoard(patient: Boolean) {
        patientOnBoard = patient
    }

}