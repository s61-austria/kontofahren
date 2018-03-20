package domain

import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import utils.now
import java.util.Date
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
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
    @Enumerated(EnumType.STRING)
    var state: InvoiceState = InvoiceState.OPEN,
    @ManyToOne
    val profile: Profile? = null,
    @ManyToOne
    val vehicle: Vehicle? = null,
    @Temporal(TemporalType.DATE)
    val generatedFor: Date,
    val kilometers: Double
) {

    @Id
    @GeneratedValue
    var id: Long = -1

    @Column(unique = true)
    val uuid: String = UUID.randomUUID().toString()

    val createdOn: Date = now()

    var expires: Date? = null

    var totalPrice: Double = 0.toDouble()
}
