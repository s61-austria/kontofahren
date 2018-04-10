package utils

import nl.stil4m.mollie.ClientBuilder
import nl.stil4m.mollie.domain.CreatePayment
import nl.stil4m.mollie.domain.Payment
import java.util.Date
import java.util.Optional

private val apiKey = "test_KamHdPyNppksPNrjqxFAWfk2d5fEdw"

fun createMolliePayment(totalPrice: Double, uuid: String, month: Date): Payment? {
    var metaData = HashMap<String, Any>().apply { this.put("invoiceId", uuid) }

    var payment = CreatePayment(Optional.empty(),
        totalPrice,
        "KontoFahren, Invoice payment. ID: " + uuid + " " + month.month + "/" + month.year,
        "http://localhost:3000/#/invoices",
        Optional.empty(),
        metaData as Map<String, Any>?
    )

    try {
        val client = ClientBuilder().withApiKey(apiKey).build()
        if (client != null) {

            var response = client.payments().create(payment)
            if (response.success) {
                return response.data
            } else {
                return null
            }
        }
    } catch (ex: Exception) {
        return null
    }

    return null
}

fun removeMolliePayment(paymentId: String) {
    var client = ClientBuilder().withApiKey(apiKey).build()
    client.payments().delete(paymentId)
}
