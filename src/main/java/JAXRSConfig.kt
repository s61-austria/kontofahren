import rest.InvoiceResource
import rest.RateResource
import rest.UserResource
import rest.VehicleResource
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("api")
class JAXRSConfig : Application()
{
    override fun getClasses(): MutableSet<Class<*>> = HashSet<Class<*>>().apply {
        add(InvoiceResource::class.java)
        add(UserResource::class.java)
        add(VehicleResource::class.java)
        add(RateResource::class.java)
    }
}