package singletons

import connector.Connector
import domain.enums.VehicleType
import logger
import model.Car
import model.Countries.AUSTRIA
import model.Invoice
import model.StolenCar
import service.VehicleService
import utils.Open
import utils.decode
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Open
@Singleton
@Startup
class EuropeanIntegration {
    @Inject
    lateinit var vehicleService: VehicleService

    val connection by lazy {
        logger.info("Instantiating European Connector class")
        Connector(
            "rabbitmq",
            "rabbitmq",
            "vhost",
            "ec2-18-197-180-1.eu-central-1.compute.amazonaws.com"
        )
    }

    @PostConstruct
    fun setup() {
        try {
            connection.subscribeToQueue(AUSTRIA, Car::class.java, {
                logger.info("Received car message from MQ")
                val car: Car = decode(it, Car::class.java)

                vehicleService.addVehicle(car.licencePlate, VehicleType.ABROAD, car.licencePlate)

                logger.debug(it)
            })

            connection.subscribeToQueue(AUSTRIA, Invoice::class.java, {
                logger.info("Received invoice message from MQ")
                logger.debug(it)
            })

            connection.subscribeToQueue(AUSTRIA, StolenCar::class.java, {
                logger.info("Received stolen car message from MQ")
                logger.debug(it)
            })
        } catch (e: Exception) {
            logger.error("Error instantiating Integration module", e)
        }
    }

    @PreDestroy
    fun close() {
        connection.close()
    }
}
