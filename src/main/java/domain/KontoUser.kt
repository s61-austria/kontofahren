package domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "kontouser")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class KontoUser {

    @Id
    private val id: String = UUID.randomUUID().toString()

    private val userName: String? = null

    private val password: String? = null

    @OneToOne
    val profile: Profile? = null


}
