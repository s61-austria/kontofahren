package serializers

import domain.Country
import domain.Vehicle
import java.io.Serializable

data class InvoiceGenerateSerializer (
    val vehicle: Vehicle,
    val country: Country,
    val month: Long,
    val expirationDate: Long,
    val invoiceUuid: String?
) : Serializable
