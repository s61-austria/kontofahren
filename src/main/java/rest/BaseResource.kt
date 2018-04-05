package rest

import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

abstract class BaseResource {
    @Context
    private lateinit var request: UriInfo

    @Context
    private lateinit var sc: SecurityContext

    val params by lazy { request.queryParameters }
    val username by lazy { sc.userPrincipal.name }
}
