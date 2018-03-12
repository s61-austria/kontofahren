package domain

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@DiscriminatorValue("Civilian")
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
