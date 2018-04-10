package rest

import jdk.nashorn.internal.objects.annotations.Getter
import service.ProfileService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
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
        val userUUID = request.queryParameters.get("userUUID")?.first()?.toString()
        val responses = profileService.getAllProfiles(userUUID)

        return Response.ok(responses).build()
    }


}
