abstract class Event(private val id: Int,
                     private var tick: Int,
                     private var duration: Int) {

    fun getId(): Int {
        return id;
    }

    fun getTick(): Int {
        return tick;
    }

    fun getDuration(): Int {
        return duration;
    }

    fun setTick(value: Int) {
        if (value >= 0)
            tick = value;
    }

    fun setDuration(value: Int) {
        if (value >= 1)
            duration = value;
    }

    abstract fun executeStart(): Boolean

    abstract fun updateEvent(): Boolean
}