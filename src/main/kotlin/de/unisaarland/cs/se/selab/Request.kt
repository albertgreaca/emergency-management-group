class Request(private val id: Int, private val emergency: Emergency,
              private val processingBase: Base) {

    fun getId(): Int {
        return id
    }

    fun getEmergency(): Emergency {
        return emergency
    }

    fun getResources(): Resource {
        return emergency.getResources()
    }

    fun getProcessingBase(): Base {
        return processingBase
    }

    fun getRequestingBase(): Base {
        return emergency.getBase()!!
    }
}