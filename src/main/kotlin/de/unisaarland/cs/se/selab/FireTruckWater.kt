package de.unisaarland.cs.se.selab

class FireTruckWater(
    private val id: Int,
    private val base: Base,
    private val staffCapacity: Int,
    private val vehicleHeight: Int,
    private var position: Position? = null,
    private val maxCapacity: Int,
    private var waterAmount: Int
) : Vehicle(
    id,
    VehicleType.FIRE_TRUCK_WATER,
    base,
    staffCapacity,
    vehicleHeight,
    position
) {
    fun getMaxCapacity(): Int {
        return maxCapacity
    }

    fun getWaterAmount(): Int {
        return waterAmount
    }
    fun setWaterAmount(water: Int) {
        waterAmount = water
    }
}
