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
    var owner: Profile? = null,
    @ManyToOne
    var vehicle: Vehicle? = null
) {

    @Id @GeneratedValue
    var id: Long = -1

    @Column(unique = true)
    val uuid: String = UUID.randomUUID().toString()

    val createdOn: Date = now()

    var expires: Date? = null

    var totalPrice: Double = 0.toDouble()
}
