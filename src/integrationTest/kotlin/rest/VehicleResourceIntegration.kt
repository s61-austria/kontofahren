package rest

import io.restassured.RestAssured.given
import org.junit.Test

class VehicleResourceIntegration : BaseResourceIntegration() {
    @Test
    fun getVehicles() {
        given().`when`()
            .get("/vehicles")
            .then()
            .statusCode(200)
    }

    @Test
fun getStolenVehicles() {
        given().with()
            .queryParam("stolen", "true")
            .get("/vehicles")
            .then()
            .statusCode(200)
    }
}
