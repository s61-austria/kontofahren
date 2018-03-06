package domain

import javax.persistence.*

@Entity
@DiscriminatorValue("CivilServant")
class CivilServant : User() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
}
