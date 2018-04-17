package annotations

import javax.ws.rs.NameBinding
import kotlin.annotation.AnnotationRetention.RUNTIME

@NameBinding
@Retention(RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class JWTTokenNeeded
