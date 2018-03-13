package domain

import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "country")
class Country {

    @Id
    private val id: String = UUID.randomUUID().toString()

    private val name: String? = null
}
