package domain

import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToOne
import javax.persistence.Table

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
