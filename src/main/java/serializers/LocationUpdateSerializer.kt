package serializers

import java.io.Serializable

data class LocationUpdateSerializer(
    val vehicleId: String,
    val lat: Double,
    val lng: Double
) : Serializable
