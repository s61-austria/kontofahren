package domain

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "location")
data class Location(
    val country: Country
) : Base() {

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
