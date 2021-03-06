package rest

import com.google.gson.Gson
import com.kontofahren.integrationslosung.Exchange
import com.kontofahren.integrationslosung.RabbitGateway
import com.kontofahren.integrationslosung.Routing
import domain.Location
import domain.Point
import logger
import serializers.LocationUpdateSerializer
import service.LocationService
import service.VehicleService
import utils.JwtUtils
import utils.Open
import utils.now
import javax.inject.Inject
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
    @Produces(MediaType.APPLICATION_JSON)
    fun postLocation(
        body: String,
        @Context header: HttpHeaders
    ): Response {
        logger.info("Receiving new location update")
        val locationUpdate = Gson().fromJson(body, LocationUpdateSerializer::class.java)

        val user = jwtUtils.loggedInUser(header)
        val vehicle = vehicleService.getVehicleByUuid(locationUpdate.vehicleId) ?: return Response.status(404).build()

        if (vehicle.owner!!.uuid != user.profile.uuid) {
            logger.warn("User does not own vehicle!")
            return Response.status(Response.Status.UNAUTHORIZED).build()
        }

        val location = Location(
            vehicle,
            Point(locationUpdate.lat, locationUpdate.lng),
            now()
        )

        logger.info("Persisting location")
        locationService.saveLocation(location)

        locationUpdate.locationId = location.uuid

        try {
            logger.info("Publishing location to MQ")
            rabbitGateway.publish(Exchange.LOCATION_EXCHANGE, locationUpdate, Routing.EMPTY)
            logger.info("Succesfully pushed to MQ")
        } catch (ex: Exception) {
            logger.error("Failed to publish location to exchange!", ex)
        }

        return Response.ok().build()
    }
}
