package domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "kontouser")
@Inheritance(strategy = InheritanceType.JOINED)
data class KontoUser(
    var userName: String,
    val password: String
) {
    @ManyToOne(targetEntity = Profile::class)
    @JsonIgnore
    lateinit var profile: Profile

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
