package domain

import domain.enums.InvoiceGenerationType
import domain.enums.InvoiceState

import javax.persistence.*
import java.io.Serializable
import java.util.Date

@Entity
@NamedQuery(name = "Invoice.allInvoices", query = "SELECT i FROM Invoice i")
class Invoice : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: String? = null
    @Column
    var createdOn: Date? = null
    @Column
    var generatedFor: Date? = null
    @Enumerated(EnumType.STRING)
    var generationType: InvoiceGenerationType? = null
    @Enumerated(EnumType.STRING)
    var state: InvoiceState? = null
    @Column
    var totalPrice: Double = 0.toDouble()
    @ManyToOne
    var civilian: Civilian? = null
    @ManyToOne
    var vehicle: Vehicle? = null

    constructor(createdOn: Date, generatedFor: Date, generationType: InvoiceGenerationType) {
        this.createdOn = createdOn
        this.generatedFor = generatedFor
        this.generationType = generationType
    }

    constructor() {}

    companion object {

        const val serialVersionUID = 1L
    }
}
