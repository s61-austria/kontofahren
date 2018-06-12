package singletons

import com.google.gson.Gson
import com.s61.integration.connector.InternationalConnector
import com.s61.integration.model.Countries.AUSTRIA
import com.s61.integration.model.InternationalCar
import com.s61.integration.model.InternationalInvoice
import com.s61.integration.model.InternationalStolenCar
import domain.enums.VehicleType
import domain.Vehicle
import logger
import service.InvoiceService
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
class EuropeanIntegration @Inject constructor(
    val invoiceService: InvoiceService,
    val vehicleService: VehicleService
) {
    val connection by lazy {
        logger.info("Instantiating European Connector class")
        InternationalConnector(
            "rabbitmq",
            "rabbitmq",
            "vhost",
            "ec2-18-197-180-1.eu-central-1.compute.amazonaws.com"
        )
    }

    @PostConstruct
    fun setup() {
        try {
            connection.subscribeToQueue(AUSTRIA, InternationalCar::class.java, {
                logger.info("Received car message from MQ")
                val car = decode(it, InternationalCar::class.java)

                vehicleService.addVehicle(car.licencePlate, VehicleType.ABROAD, car.licencePlate)

                logger.debug(it)
            })

            connection.subscribeToQueue(AUSTRIA, InternationalInvoice::class.java, {
                logger.info("Received invoice message from MQ")
                val invoice = decode(it, InternationalInvoice::class.java)

                invoiceService.saveForeignInvoice(invoice)

                logger.debug(it)
            })

            connection.subscribeToQueue(AUSTRIA, InternationalStolenCar::class.java, {
                logger.info("Received stolen car message from MQ")
                val stolenCar = Gson().fromJson(it, InternationalStolenCar::class.java)
                val vehicles = vehicleService.allVehicles()

                val vehicle = vehicles.filter { it.licensePlate == stolenCar.licencePlate }.firstOrNull()
                    ?: Vehicle("", stolenCar.licencePlate, VehicleType.PKW)

                vehicle.isStolen = stolenCar.isStolen

                vehicleService.updateVehicle(vehicle)

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
