package rest

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application
import java.util.HashSet

@ApplicationPath("api")
class JAXRSConfig : Application() {
    override fun getClasses(): Set<Class<*>> {
        val classes = HashSet<Class<*>>()
        classes.add(InvoiceResource::class.java)
        classes.add(VehicleResource::class.java)
        return classes

    }
}
