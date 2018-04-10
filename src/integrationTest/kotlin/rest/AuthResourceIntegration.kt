package rest

import io.restassured.RestAssured.given
import org.junit.Test
import javax.ws.rs.core.MediaType

class AuthResourceIntegration : BaseResourceIntegration() {
    @Test
    fun testLogin() {
        val token = given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapOf(
                "username" to "Michel Mans",
                "password" to "password2"
            ))
            .`when`()
            .post("/auth/login")
            .then()
            .statusCode(200)

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapOf(
                "username" to "Knoert Krachelbek",
                "password" to "correct horse battery staple"
            ))
            .`when`()
            .post("/auth/login")
            .then()
            .statusCode(204)
    }

    @Test
    fun testProtected() {
        given()
            .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrb250b2ZhaHJlbi13aWRsZmx5IiwiZ3JvdXBzIjpbXSwiaWQiOiJjOThlMWVjZS1hYWM1LTQwY2ItOWMwNS0wYzg1NGNmMGNlOTciLCJleHAiOjE1MjU5NDc2NTMsImlhdCI6MTUyMzM1NTY1MywidXNlcm5hbWUiOiJNaWNoZWwgTWFucyJ9.U_k_FgygOt7Qlz_OoybxtvOG-sYDoOab-s2HXfxZrWY")
            .`when`()
            .get("auth")
            .then()
            .statusCode(200)

        given()
            .header("Authorization", "haha")
            .`when`()
            .get("auth")
            .then()
            .statusCode(204)

        given()
            .`when`()
            .get("auth")
            .then()
            .statusCode(204)
    }
}
