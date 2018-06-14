package serializers

import java.io.Serializable

data class InvoiceGenerateSerializer (
    val vehicleUuid: String,
    val countryUuid: String,
    val month: Long,
    val expirationDate: Long,
    val invoiceUuid: String?
) : Serializable
