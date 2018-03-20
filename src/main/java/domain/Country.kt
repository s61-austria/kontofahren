package domain

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "country")
data class Country(
    var name: String
) {
    @Id
    var id: String = UUID.randomUUID().toString()
}
