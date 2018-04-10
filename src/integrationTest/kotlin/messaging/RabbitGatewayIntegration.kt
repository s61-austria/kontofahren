package messaging

import messaging.Exchange.LOCATION_EXCHANGE
import messaging.Queue.FRONTEND_LOCATION_UPDATE
import messaging.Routing.EMPTY
import org.junit.Test

class RabbitGatewayIntegration {
    val rabbitGateway by lazy { RabbitGateway() }

    @Test
    fun basicPublish() {
        val obj = mapOf("hello" to "world")
        rabbitGateway.publish(LOCATION_EXCHANGE, obj, EMPTY)
    }

    @Test
    fun basicConsume() {
        rabbitGateway.consume(FRONTEND_LOCATION_UPDATE, {
            println(it)
            return@consume
        })
        Thread.sleep(2000)
    }
}
