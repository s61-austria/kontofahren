package domain

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "kontouser")
@Inheritance(strategy = InheritanceType.JOINED)
data class KontoUser(
    val userName: String,
    val password: String,
    val profile: Profile
) : Base() {

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
