package domain

import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "location")
class Location {

    @Id
    private val id: String = UUID.randomUUID().toString()

    @ManyToOne
    private val country: Country? = null
}
