package domain

import java.util.*
import javax.persistence.*

@Entity
class Location {

    @Id
    private val id: String = UUID.randomUUID().toString()

    @ManyToOne
    private val country: Country? = null
}
