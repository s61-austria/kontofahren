import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider
import java.io.IOException

/**
 * Created by Wouter Vanmulken on 8-4-2017.
 */
@Provider
class CORSFilter : ContainerResponseFilter {
    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext,
                        cres: ContainerResponseContext) {

        cres.headers.add("Access-Control-Allow-Origin", "*")
        cres.headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
        cres.headers.add("Access-Control-Allow-Credentials", "true")
        cres.headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
        cres.headers.add("Access-Control-Max-Age", "1209600")
    }
}