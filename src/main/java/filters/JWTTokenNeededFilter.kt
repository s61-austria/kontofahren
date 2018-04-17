package filters

import annotations.JWTTokenNeeded
import logger
import utils.Open
import utils.verifyToken
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
@Open
open class JWTTokenNeededFilter : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext) {
        logger.info("Validating JWT token")
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) ?: "ERROR: NO HEADER FOUND"

        val token = authorizationHeader.substring("Bearer".length).trim()

        if (verifyToken(token) != null) {
            logger.info("Token validates succesfully")
        } else {
            logger.warn("Token not valid")
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        }
    }
}
