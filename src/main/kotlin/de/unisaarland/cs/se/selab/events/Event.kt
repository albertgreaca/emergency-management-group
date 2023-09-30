package de.unisaarland.cs.se.selab.events

/** Class for the Events
 */
abstract class Event(
    open val id: Int,
    open var tick: Int,
    open var duration: Int
) {
    /**
     * starts event for the first time
     * return true if event could be started
     */
    abstract fun executeStart(): Boolean

    /**
     *stops events that ended
     */
    abstract fun stopEvent()
}
