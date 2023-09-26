package de.unisaarland.cs.se.selab

/** Class for the Events
 */
abstract class Event(
    open val id: Int,
    open var tick: Int,
    open var duration: Int
) {

    /**
     * setter for the starting tick
     */
    fun setTick(value: Int) {
        if (value >= 0) {
            tick = value
        }
    }

    /**
     * setter for the duration
     */
    fun setDuration(value: Int) {
        if (value >= 1) {
            duration = value
        }
    }

    /**
     * starts event for the first time
     * return true if event could be started
     */
    abstract fun executeStart(): Boolean

    /**
     *updates state of event if it already had started
     * returns true if event could be updated
     */
    abstract fun stopEvent()
}
