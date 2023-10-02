package de.unisaarland.cs.se.selab.resources

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency

/**
 * Class to save requests
 */
class Request(
    val id: Int,
    val emergency: Emergency,
    val processingBase: Base
) {
    /**
     * @return Base which requested (responsible base for emergency)
     */
    fun getRequestingBase(): Base {
        return requireNotNull(emergency.base)
    }
}
