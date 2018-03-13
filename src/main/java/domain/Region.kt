package domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "region")
data class Region(
    val name: String
) {
    @Id
    private val id: String = UUID.randomUUID().toString()
}
