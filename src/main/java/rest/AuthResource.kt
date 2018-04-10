package rest

import com.google.gson.Gson
import serializers.LoginSerializer
import utils.JwtUtils
import utils.Open
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Open
@Path("auth")
class AuthResource @Inject constructor(val jwtUtils: JwtUtils) {
    /**
     * Check a token for validity
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(
        token: String,
        @Context header: HttpHeaders
    ): Response {
        val token = header.getHeaderString("Authorization") ?: return Response.noContent().build()

        return if (jwtUtils.isLoggedIn(token)) Response.ok().build() else Response.noContent().build()
    }

    /**
     * Log a user in
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun login(
        input: String
    ): Response? {
        val loginSerializer = Gson().fromJson(input, LoginSerializer::class.java)

        val token = jwtUtils.login(loginSerializer.username, loginSerializer.password)
            ?: return Response.noContent().build()

        return Response.ok(token).build()
    }

    /**
     * Protected resource
     */
}
