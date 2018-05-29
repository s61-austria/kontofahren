package rest

import io.restassured.RestAssured.given
import org.junit.Test
import java.util.UUID

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

    @Test
    fun putStolenVehicle() {
        // update manually
        val vehicleid = UUID.fromString("5723a586-4e72-42bf-88d1-e7d2dd5bb28b")
        given().`when`().put("/vehicles/$vehicleid/stolen")
            .then()
            .statusCode(200)
    }

    @Test
    fun deleteStolenVehicle() {
        // update manually
        val vehicleid = UUID.fromString("5723a586-4e72-42bf-88d1-e7d2dd5bb28b")
        given().`when`().delete("/vehicles/$vehicleid/stolen")
            .then()
            .statusCode(200)
    }
}
