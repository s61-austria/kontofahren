package rest

import service.ProfileService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("profiles")
@Open
class ProfileResource @Inject constructor(
    private val profileService: ProfileService
) {
    @Context
    private lateinit var request: UriInfo

    @GET
    @Produces("application/json")
    fun getAllProfiles(): Response {
        val vehicleUUID = request.queryParameters.get("vehicleUUID")?.first()?.toString()
        val responses = profileService.getAllProfiles(vehicleUUID)

        return Response.ok(responses).build()
    }

    @GET
    @Path("/{uuid}")
    @Produces("application/json")
    fun getProfile(@PathParam("uuid") userUUID: String): Response {
        val profile = profileService.getProfile(userUUID) ?: return Response.status(404).build()
        return Response.ok(profile).build()
    }
}
