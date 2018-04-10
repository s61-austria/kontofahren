package serializers

data class LocationUpdateSerializer(
    val vehicleUid: String,
    val lat: Double,
    val lng: Double
)
