package domain

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "activity")
class Activity : Serializable {

    @Id
    private val id: String = UUID.randomUUID().toString()

    companion object {

        private const val serialVersionUID = 1L
    }
}
