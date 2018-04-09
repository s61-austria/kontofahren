package rest

import domain.KontoUser
import service.UserService
import utils.Open
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo

@Path("users")
@Open
class UserResource @Inject constructor(
    private val userService: UserService
) {
    @Context
    private lateinit var request: UriInfo

    @GET
    @Produces("application/json")
    fun allUsers(): List<KontoUser> = userService.allUsers()
}
