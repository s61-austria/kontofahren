package rest

import io.restassured.RestAssured
import org.junit.Before

abstract class BaseResourceIntegration {
    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrb250b2ZhaHJlbi13aWRsZmx5IiwiZ3JvdXBzIjpbXSwiaWQiOiJmZGQzYmYzOC05ZjlkLTQ5NGEtOWNjNC02NTc2NDYwOTA3YmYiLCJleHAiOjE1Mjk0MTk2MTYsImlhdCI6MTUyNjc0MTIxNiwidXNlcm5hbWUiOiJNaWNoZWwgTWFucyJ9.RwiDW469XPKxd6m12c-reltw6TulT_6YwallFKxXZKI"

    @Before
    fun setupRestAssured() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/government/api/"
        RestAssured.port = 8080
    }
}
