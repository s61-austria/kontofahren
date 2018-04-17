package messaging

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.BuiltinExchangeType.FANOUT
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import logger
import messaging.Exchange.LOCATION_EXCHANGE
import messaging.Routing.EMPTY
import utils.GsonWrapper
import javax.ws.rs.core.MediaType

class RabbitGateway(
    val username: String = "user",
    val password: String = "pass",
    val vhost: String = "vhost",
    val host: String = "localhost",
    val port: Int = 5672
) {
    /**
     * Factory for connections to
     */
    private val factory by lazy {
        ConnectionFactory().apply {
            logger.info("Instantiation Rabbit Gateway ${this@RabbitGateway}")
            username = this@RabbitGateway.username
            password = this@RabbitGateway.password
            virtualHost = this@RabbitGateway.vhost
            host = this@RabbitGateway.host
            port = this@RabbitGateway.port
        }
    }

    private val connection by lazy {
        logger.info("Instantiation new rabbit connection")
        this.factory.newConnection()
    }

    private val channel by lazy {
        logger.info("Instantiating new channel")
        connection.createChannel()
    }

    init {
        logger.info("Creating exchanges")
        exchangeDeclare(Exchange.LOCATION_EXCHANGE, FANOUT)

        logger.info("Creating queues")
        queueDeclare(Queue.FRONTEND_LOCATION_UPDATE)
        queueDeclare(Queue.LOCATION_TO_ACTIVITY)

        logger.info("Binding queues to exchanges")
        queueBind(Queue.FRONTEND_LOCATION_UPDATE, LOCATION_EXCHANGE, EMPTY)
        queueBind(Queue.LOCATION_TO_ACTIVITY, LOCATION_EXCHANGE, EMPTY)
    }

    private fun exchangeDeclare(exchange: Exchange, type: BuiltinExchangeType) = channel.exchangeDeclare(exchange.name, type)
    private fun queueDeclare(queue: Queue, durable: Boolean = true, exclusive: Boolean = false, autoDelete: Boolean = false, config: Map<String, String> = emptyMap()) = channel.queueDeclare(queue.name, durable, exclusive, autoDelete, config)
    private fun queueBind(queue: Queue, exchange: Exchange, routing: Routing) = channel.queueBind(queue.name, exchange.name, routing.name)

    /**
     * Attach a handler function to a queue
     * @param queue Name of the Queue to attach to
     * @param handler handler function that receives a JSON obj
     *
     */
    fun consume(queue: String, handler: (String) -> Unit) {
        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: ByteArray) {
                val tag = envelope.deliveryTag
                handler(body.toString(Charsets.UTF_8))
                channel.basicAck(tag, false)
            }
        }

        channel.basicConsume(queue, false, consumer)
    }

    /**
     * @see RabbitGateway.consume
     */
    fun consume(queue: Queue, handler: (String) -> Unit) = consume(queue.name, handler)

    /**
     *  Create a one off queue in an exchange
     */
    fun createExclusiveQueue(exchange: Exchange, routing: Routing = Routing.EMPTY) = channel.queueDeclare().apply {
        channel.queueBind(queue, exchange.name, routing.name)
    }.queue

    /**
     * Publish a message to an exchange
     * @param exchange Name of the exchange
     * @param obj Object  to encode as JSON in body
     * @param routing Routing to assign
     * @param deliveryMode mode of delivery. Default is persist
     */
    fun publish(
        exchange: String,
        obj: Any,
        routing: String,
        deliveryMode: Int = 2
    ) {
        val props = BasicProperties.Builder()
            .contentType(MediaType.APPLICATION_JSON)
            .deliveryMode(deliveryMode)
            .build()
        val json = GsonWrapper.gson.toJson(obj)
        channel.basicPublish(exchange, routing, props, json.toByteArray(Charsets.UTF_8))
    }

    /**
     * @see publish
     */
    fun publish(
        exchange: Exchange,
        obj: Any,
        routing: Routing,
        deliverMode: Int = 2
    ) = publish(exchange.name, obj, routing.name, deliverMode)
}

enum class Exchange {
    LOCATION_EXCHANGE,
}

enum class Queue {
    FRONTEND_LOCATION_UPDATE,
    LOCATION_TO_ACTIVITY
}

enum class Routing {
    EMPTY
}
