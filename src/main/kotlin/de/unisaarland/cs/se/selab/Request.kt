package de.unisaarland.cs.se.selab

/**
 * Class to save requests
 */
class Request(
    private val id: Int,
    private val emergency: Emergency,
    private val processingBase: Base
) {
    /**
     * @return Request ID
     */
    fun getId(): Int {
        return id
    }

    /**
     * @return Emergency the request belongs to
     */
    fun getEmergency(): Emergency {
        return emergency
    }

    /**
     * @return the Resources that are being requested
     */
    fun getResources(): Resource {
        return emergency.resources
    }

    /**
     * @return Base which processes request atm
     */
    fun getProcessingBase(): Base {
        return processingBase
    }

    /**
     * @return Base which requested (responsible base for emergency)
     */
    fun getRequestingBase(): Base {
        return requireNotNull(emergency.base)
    }
}
