package singletons

import com.google.gson.Gson
import com.kontofahren.integrationslosung.Queue
import com.kontofahren.integrationslosung.RabbitGateway
import domain.Activity
import logger
import serializers.LocationUpdateSerializer
import service.ActivityService
import service.LocationService
import service.VehicleService
import utils.Open
import utils.before
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Open
@Singleton
@Startup
class LocationActivityTranslatorSingleton @Inject constructor(
    val vehicleService: VehicleService,
    val locationService: LocationService,
    val activityService: ActivityService
) {
    val rabbitGateway by lazy { RabbitGateway() }

    @PostConstruct
    fun setup() {
        logger.info("Setting up Location Activity Translator")
        rabbitGateway.consume(Queue.LOCATION_TO_ACTIVITY, { locationToActivity(it) })
    }

    fun locationToActivity(body: String) {
        logger.info("Received location to merge with activity")
        val decoded = Gson().fromJson(body, LocationUpdateSerializer::class.java)

        val vehicle = vehicleService.getVehicleByUuid(decoded.vehicleId) ?: throw Exception("Failed to get vehicle")
        val location = locationService.getLocation(decoded.locationId!!) ?: throw Exception("Failed to get location")
        val activities = vehicle.activities

        val latestActivity = activities.filter { it.creation.dayOfYear() == location.creation.dayOfYear() }
            .filter { it.locations.isNotEmpty() }
            .sortedBy {
                it.locations.map { it.creation }.first()
            }.firstOrNull()

        if (latestActivity == null) {
            logger.info("No activity for current vehicle yet")
            val activity = Activity(vehicle.owner!!, vehicle)
            activityService.create(activity)
            activity.locations.add(location)
            location.activity = activity

            locationService.updateLocation(location)
        } else {
            logger.info("Latest activity ${latestActivity.uuid}")

            val latestLocation = latestActivity.locations.sortedByDescending { it.creation }.first()
            val latestPossibleTime = latestLocation.creation.plusMinutes(30)

            if (location.creation before latestPossibleTime) {
                logger.info("Adding location to existing activity")

                latestActivity.locations.add(location)

                location.activity = latestActivity
                locationService.updateLocation(location)
            } else {
                logger.warn("No activity that matches the current vehicle, creating new activity")
                val newActivity = Activity(
                    vehicle.owner!!,
                    vehicle
                )

                activityService.create(newActivity)

                newActivity.locations.add(location)
                location.activity = newActivity

                locationService.updateLocation(location)
            }

            logger.info("Done with ${location.uuid}")
        }
    }
}
