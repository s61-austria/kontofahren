package domain

import javax.persistence.*

@Entity
@DiscriminatorValue("CivilServant")
class CivilServant : KontoUser() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
}
