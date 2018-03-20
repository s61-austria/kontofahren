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
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@NamedQuery(name = "Invoice.allInvoices", query = "SELECT i FROM Invoice i")
@Table(name = "invoice")
data class Invoice(
    @Enumerated(EnumType.STRING)
    val generationType: InvoiceGenerationType,
    @ManyToOne
    val profile: Profile,
    @ManyToOne
    val vehicle: Vehicle,
    @Temporal(TemporalType.DATE)
    val generatedFor: Date,
    val kilometers: Double
) {

    @Id
    val id: String = UUID.randomUUID().toString()
    @Enumerated(EnumType.STRING)
    var state: InvoiceState = InvoiceState.OPEN
    @Temporal(TemporalType.DATE)
    val createdOn: Date = now()
    var totalPrice: Double = vehicle.rate.kmPrice * kilometers
}
