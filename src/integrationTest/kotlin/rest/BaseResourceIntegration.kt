package rest

import io.restassured.RestAssured
import org.junit.Before

abstract class BaseResourceIntegration {
    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrb250b2ZhaHJlbi13aWRsZmx5IiwiZ3JvdXBzIjpbXSwiaWQiOiJhMzgzNjJjMi1hNWFmLTRiYTYtODIyMi02NjZlZGNiZWI5ZjMiLCJleHAiOjE1MjY1NDQ4MDQsImlhdCI6MTUyMzk1MjgwNCwidXNlcm5hbWUiOiJNaWNoZWwgTWFucyJ9.AQ3VLwCqHzR2Zr3RIpdEdvfmZN-BP699wAw1vFsmxQk"

    @Before
    fun setupRestAssured() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/s61-proftaak-1.0-SNAPSHOT/api"
        RestAssured.port = 8080
    }
}
