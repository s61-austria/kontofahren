package domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Table(name = "kontouser")
@Inheritance(strategy = InheritanceType.JOINED)
data class KontoUser(
    val userName: String,
    val password: String,
    val profile: Profile? = null
) {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
