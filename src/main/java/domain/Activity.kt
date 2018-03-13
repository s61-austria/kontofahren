package domain

import java.io.Serializable
import java.util.UUID // ktlint-disable no-wildcard-imports
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "activity")
class Activity : Serializable {

    @Id
    private val id: String = UUID.randomUUID().toString()

    companion object {

        private const val serialVersionUID = 1L
    }
}
