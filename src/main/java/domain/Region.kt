package domain

import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "region")
class Region {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
