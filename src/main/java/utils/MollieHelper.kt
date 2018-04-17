package utils

import nl.stil4m.mollie.ClientBuilder
import nl.stil4m.mollie.domain.CreatePayment
import nl.stil4m.mollie.domain.Payment
import java.util.Optional
import java.util.Date
import javax.ejb.Stateless

private val apiKey = "test_jg624zMCDTzB8EDJFtCp2FHCjuBGRy"

@Stateless
class MollieHelper {

    fun createMolliePayment(uuid: String, totalPrice: Double, month: Date): Payment? {
        var metaData = HashMap<String, Any>().apply { this["invoiceId"] = uuid }

        var payment = CreatePayment(
            Optional.empty(),
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
}
