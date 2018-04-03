package domain

import java.io.Serializable

data class Point(
    val lat: Double,
    val lng: Double
) : Serializable {
    fun distanceBetween(point: Point): Double {
        val Radius = 6378.137 // Radius of earth in km.

        val lat1 = this.lat
        val lon1 = this.lng
        val lat2 = point.lat
        val lon2 = point.lng

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
