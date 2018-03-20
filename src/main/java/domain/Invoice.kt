package domain

import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import utils.now
import java.util.Date
import java.util.UUID
import javax.persistence.*

@Entity
@NamedQuery(name = "Invoice.allInvoices", query = "SELECT i FROM Invoice i")
@Table(name = "invoice")
data class Invoice(
    @Enumerated(EnumType.STRING)
    var generationType: InvoiceGenerationType,
    @Enumerated(EnumType.STRING)
    var state: InvoiceState = InvoiceState.OPEN,
    @ManyToOne
    var profile: Profile? = null,
    @ManyToOne
    var vehicle: Vehicle? = null
) : Base() {

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    val createdOn: Date = now()

    var generatedFor: Date? = null

    var totalPrice: Double = 0.toDouble()
}
