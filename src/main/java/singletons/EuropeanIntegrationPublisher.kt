package singletons

import connector.Connector
import logger
import model.Car
import javax.ejb.Stateless

@Stateless
class EuropeanIntegrationPublisher {
    val connection by lazy {
        logger.info("Instantiating European Connector class")
        Connector(
            "rabbitmq",
            "rabbitmq",
            "vhost",
            "ec2-18-197-180-1.eu-central-1.compute.amazonaws.com"
        )
    }

    fun publishCar(car: Car): Boolean {
        connection.publishCar(car)

        return true
    }
}
