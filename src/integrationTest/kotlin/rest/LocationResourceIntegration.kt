package rest

import io.restassured.RestAssured.given
import org.junit.Test
import serializers.LocationUpdateSerializer

class LocationResourceIntegration : BaseResourceIntegration() {
    @Test
    fun pushLocationUpdate() {
        val body = LocationUpdateSerializer(
            "9a9dfe2e-5709-4f52-ade4-4e18ec30570e", // Set manually
            0.0,
            0.0
        )

        given().body(body)
            .header("Authorization", "Bearer $token")
            .`when`()
            .post("/locations")
            .then()
            .statusCode(200)
    }
}
