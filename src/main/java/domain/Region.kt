package domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "region")
class Region {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
