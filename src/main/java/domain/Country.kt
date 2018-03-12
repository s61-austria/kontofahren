package domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "country")
class Country {

    @Id
    private val id: String = UUID.randomUUID().toString()

    private val name: String? = null
}
