package rest

import io.restassured.RestAssured
import org.junit.Before

abstract class BaseResourceIntegration {
    @Before
    fun setupRestAssured() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/s61-proftaak-1.0-SNAPSHOT/api"
        RestAssured.port = 8080
    }
}
