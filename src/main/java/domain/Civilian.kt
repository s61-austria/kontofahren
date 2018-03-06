package domain

import javax.persistence.*
import java.io.Serializable

@Entity
@DiscriminatorValue("Civilian")
class Civilian : User(), Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToMany
    var vehicles: List<Vehicle>? = null
    @OneToMany
    var invoices: List<Invoice>? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
