package de.unisaarland.cs.se.selab

/** Class for the Fire Truck Water
 */
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
    /**
     * getter for the maximal Capacity
     * @return maximal capacity of the vehicle
     */
    fun getMaxCapacity(): Int {
        return maxCapacity
    }

    /**
     * getter for the water amount
     * @return water amount of the vehicle
     */
    fun getWaterAmount(): Int {
        return waterAmount
    }

    /**
     * setter for the amount of water
     */
    fun setWaterAmount(water: Int) {
        waterAmount = water
    }
}
