package rest

import annotations.JWTTokenNeeded
import com.google.gson.Gson
import serializers.LoginSerializer
import serializers.RegisterSerializer
import utils.JwtUtils
import utils.Open
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("auth")
@Open
class AuthResource @Inject constructor(val jwtUtils: JwtUtils) {

    /**
     * Check a token for validity
     */
    @GET
    @JWTTokenNeeded
    @Produces("application/json")
    fun get() = Response.ok().build()

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

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun register(input: String): Response? {
        val registerSerializer = Gson().fromJson(input, RegisterSerializer::class.java)

        val token = jwtUtils.register(registerSerializer.username, registerSerializer.password)
            ?: return Response.noContent().build()

        return Response.ok(token).build()
    }

    /**
     * Protected resource
     */
}
