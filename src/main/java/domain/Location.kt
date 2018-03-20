package domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "location")
data class Location(
    val country: Country
) {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
