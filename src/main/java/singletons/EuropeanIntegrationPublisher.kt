package singletons

import com.s61.integration.connector.InternationalConnector
import com.s61.integration.model.InternationalCar
import logger
import javax.ejb.Stateless

@Stateless
class EuropeanIntegrationPublisher {
    val connection by lazy {
        logger.info("Instantiating European Connector class")
        InternationalConnector(
            "rabbitmq",
            "rabbitmq",
            "vhost",
            "ec2-18-197-180-1.eu-central-1.compute.amazonaws.com"
        )
    }

    fun publishCar(car: InternationalCar): Boolean {
        connection.publishCar(car)

        return true
    }
}
