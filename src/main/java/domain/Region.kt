package domain

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "region")
data class Region(
    val name: String
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
