package utils

import domain.Location

/**
 * Created by Marc on 2-5-2017.
 */
object GeoUtil {

    /**
     * Measures the distance between to geographic coordinates in meters.
     *
     * @param l1 Location 1
     * @param l2 Location 2
     * @return Distance between two locations in meters.
     */
    fun measureGeoDistance(l1: Location, l2: Location): Double {

        val Radius = 6378.137 // Radius of earth in km.

        val lat1 = l1.latitude
        val lon1 = l1.longitude
        val lat2 = l2.latitude
        val lon2 = l2.longitude

        val lat1Rad = Math.toRadians(lat1) // Latitude 1 in radians.
        val lat2Rad = Math.toRadians(lat2) // Latitude 2 in radians.

        val diffLat = Math.toRadians(lat2 - lat1) // Latitude 2 - Latitude 2 in radians.
        val diffLon = Math.toRadians(lon2 - lon1) // Longitude 2 - Longitude 2 in radians.

        // Haversine formula for "a"
        val a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(diffLon / 2) * Math.sin(diffLon / 2)

        // Haversine formula for "c"
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        // Distance from radius
        val d = Radius * c

        // Distance in meters
        return d * 1000
    }
}
