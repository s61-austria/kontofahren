package messaging

import com.kontofahren.integrationslosung.Exchange
import com.kontofahren.integrationslosung.Queue
import com.kontofahren.integrationslosung.RabbitGateway
import com.kontofahren.integrationslosung.Routing
import org.junit.Test

class RabbitGatewayIntegration {
    val rabbitGateway by lazy { RabbitGateway() }

    @Test
    fun basicPublish() {
        val obj = mapOf("hello" to "world")
        rabbitGateway.publish(Exchange.LOCATION_EXCHANGE, obj, Routing.EMPTY)
    }

    @Test
    fun basicConsume() {
        rabbitGateway.consume(Queue.FRONTEND_LOCATION_UPDATE, {
            println(it)
            return@consume
        })
        Thread.sleep(2000)
    }
}
