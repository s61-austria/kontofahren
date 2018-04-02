package domain

import org.junit.Test
import kotlin.test.assertEquals

class PointTest {
    @Test
    fun testDistance() {
        val point1 = Point(49.444733, 11.077771)
        val point2 = Point(52.501443, 13.393015)

        val dist = point1.distanceBetween(point2)

        assertEquals(376942.57783687254, dist)
    }
}
