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
import javax.persistence.GenerationType
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
    @Temporal(TemporalType.DATE)
    val expires: Date = now(),
    val meters: Double = 0.0
) {
    @ManyToOne
    lateinit var profile: Profile

    @ManyToOne
    lateinit var vehicle: Vehicle

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()

    val createdOn: Date = now()

    var totalPrice: Double = 0.toDouble()
}
