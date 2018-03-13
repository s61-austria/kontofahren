package rest

import domain.KontoUser
import service.UserService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("users")

class UserResource @Inject constructor(
    val userService: UserService
) {

    @GET
    @Produces("application/json")
    fun allUsers(): List<KontoUser> = userService.allUsers()
}
