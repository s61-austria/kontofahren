package rest

import domain.Country
import domain.Location
import domain.Point
import logger
import messaging.Exchange.LOCATION_EXCHANGE
import messaging.RabbitGateway
import messaging.Routing.EMPTY
import serializers.LocationUpdateSerializer
import service.LocationService
import service.VehicleService
import utils.JwtUtils
import utils.Open
import utils.now
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("locations")
@Open
class LocationResource @Inject constructor(
    val vehicleService: VehicleService,
    val locationService: LocationService,
    val jwtUtils: JwtUtils
) {
    val rabbitGateway get() = RabbitGateway()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postLocation(
        locationUpdate: LocationUpdateSerializer,
        @Context header: HttpHeaders
    ): Response {
        logger.info("Receiving new location update")
        val token = header.getHeaderString("Authorization") ?: return Response.noContent().build()

        if (!jwtUtils.isLoggedIn(token)) {
            logger.warn("User was not logged in!")
            Response.noContent().build()
        }

        val user = jwtUtils.loggedInUser(token) ?: return Response.status(404).build()
        val vehicle = vehicleService.getVehicleByUuid(locationUpdate.vehicleUid) ?: return Response.status(404).build()

        val location = Location(
            Country("Austria"),
            Point(locationUpdate.lat, locationUpdate.lng),
            now()
        )

        logger.info("Persisting location")
        locationService.saveLocation(location)

        logger.info("Publishing location to MQ")
        rabbitGateway.publish(LOCATION_EXCHANGE, location, EMPTY)

        return Response.ok().build()
    }
}
