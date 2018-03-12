package domain

import javax.persistence.*

@Entity
@Table(name = "KontoUser")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class KontoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null


    private val userName: String? = null

    private val password: String? = null

    @OneToOne
    val profile: Profile? = null


}
