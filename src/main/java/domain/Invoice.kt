package domain

import domain.enums.InvoiceGenerationType

import javax.persistence.*
import java.io.Serializable
import java.util.Date

@Entity
class Invoice : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column
    var createdOn: Date? = null
    @Column
    var generatedFor: Date? = null
    @Enumerated(EnumType.STRING)
    var generationType: InvoiceGenerationType? = null
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

    constructor() {}

    companion object {

        val serialVersionUID = 1L
    }
}
