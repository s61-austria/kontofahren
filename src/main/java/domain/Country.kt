package domain

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "country")
data class Country(
    var name: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
