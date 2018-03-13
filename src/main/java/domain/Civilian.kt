package domain

import java.io.Serializable
import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany

@Entity
@DiscriminatorValue("civilian")
class Civilian : KontoUser(), Serializable {

    @Id
    var id: String = UUID.randomUUID().toString()

    @ManyToMany
    var vehicles: List<Vehicle>? = null
    @OneToMany
    var invoices: List<Invoice>? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
