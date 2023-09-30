package de.unisaarland.cs.se.selab.bases.departments

import de.unisaarland.cs.se.selab.bases.Base
import de.unisaarland.cs.se.selab.emergencies.Emergency

/**
 * interface for the Departments
 */
interface EmergencyObserver {
    val bases: MutableList<Base>

    /**
     * adds a Base to the Base_list
     */
    fun addBase(b: Base)

    /**
     * find a base via ID
     */
    fun findBase(id: Int): Base?

    /**
     give the departments the list of Emergencies to handle
     */
    fun update(em: Emergency)
}
