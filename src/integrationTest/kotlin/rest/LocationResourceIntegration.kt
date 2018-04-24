package rest

import io.restassured.RestAssured.given
import org.junit.Test
import serializers.LocationUpdateSerializer

class LocationResourceIntegration : BaseResourceIntegration() {
    @Test
    fun pushLocationUpdate() {
        val body = LocationUpdateSerializer(
            "e38822fa-4280-45a6-84f0-bf722480ee3f", // Set manually
            0.0,
            0.0,
            null
        )

        given().body(body)
            .header("Authorization", "Bearer $token")
            .`when`()
            .post("/locations")
            .then()
            .statusCode(200)
    }
}
