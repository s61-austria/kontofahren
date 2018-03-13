package domain

import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import utils.now
import java.util.Date
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.NamedQuery
import javax.persistence.Table

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
) {

    @Id
    val id: String = UUID.randomUUID().toString()

    val createdOn: Date = now()

    var generatedFor: Date? = null

    var totalPrice: Double = 0.toDouble()
}
