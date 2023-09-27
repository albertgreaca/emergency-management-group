import de.unisaarland.cs.se.selab.Resource
import de.unisaarland.cs.se.selab.VehicleType
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResourceTest {

    @Test
    fun isEmpty1() {
        val res = Resource(mutableListOf(), 0, 0, 0, null)
        assertTrue(res.isEmpty())
    }

    @Test
    fun isEmpty2() {
        val res = Resource(mutableListOf(), 0, 0, 0, 0)
        assertTrue(res.isEmpty())
    }

    @Test
    fun isEmptyfalse() {
        val res = Resource(mutableListOf(VehicleType.AMBULANCE), 1, 4, 5, 0)
        assertFalse(res.isEmpty())
    }
}
