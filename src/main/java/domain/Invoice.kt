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
class Invoice{
    @Enumerated(EnumType.STRING)
    lateinit var generationType: InvoiceGenerationType
    @ManyToOne
    lateinit var profile: Profile
    @ManyToOne
    lateinit var vehicle: Vehicle
    @Temporal(TemporalType.DATE)
    lateinit var generatedFor: Date
    var kilometers: Double = 0.0


    @Id
    val id: String = UUID.randomUUID().toString()
    @Enumerated(EnumType.STRING)
    var state: InvoiceState = InvoiceState.OPEN
    @Temporal(TemporalType.DATE)
    val createdOn: Date = now()
    var totalPrice: Double = 0.0
}
