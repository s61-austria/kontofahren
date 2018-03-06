package domain

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null


    private val userName: String? = null

    private val password: String? = null

    @OneToOne
    val profile: Profile? = null


}
