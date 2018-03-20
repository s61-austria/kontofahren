package domain

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "region")
data class Region(
    val name: String
) :Base() {

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
