package domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Region {
    @Id
    private val id: String? = UUID.randomUUID().toString()
}
