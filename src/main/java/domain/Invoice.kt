package domain

import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState
import java.io.Serializable
import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Column
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
class Invoice : Serializable {

    @Id
    var id: String = UUID.randomUUID().toString()
    @Column
    var createdOn: Date? = null
    @Column
    var generatedFor: Date? = null
    @Enumerated(EnumType.STRING)
    var generationType: InvoiceGenerationType? = null
    @Enumerated(EnumType.STRING)
    var state: InvoiceState = InvoiceState.OPEN
    @Column
    var totalPrice: Double = 0.toDouble()
    @ManyToOne
    var profile: Profile? = null
    @ManyToOne
    var vehicle: Vehicle? = null

    constructor(createdOn: Date, generatedFor: Date, generationType: InvoiceGenerationType) {
        this.createdOn = createdOn
        this.generatedFor = generatedFor
        this.generationType = generationType
    }

    companion object {

        val serialVersionUID = 1L
    }
}
