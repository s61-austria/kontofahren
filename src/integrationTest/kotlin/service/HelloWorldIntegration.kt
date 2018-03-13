package service

import io.restassured.RestAssured.given
import org.junit.Test

class HelloWorldIntegration {
    @Test
    fun helloWorld(){
        given().get("https://google.com")
            .then()
            .statusCode(200)
    }
}
