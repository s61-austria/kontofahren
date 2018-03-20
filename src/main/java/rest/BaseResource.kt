package rest

import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

abstract class BaseResource {
    @Context
    lateinit var request: UriInfo

    @Context
    lateinit var sc: SecurityContext

    val params by lazy { request.queryParameters }
    val username by lazy { sc.userPrincipal.name }
}
