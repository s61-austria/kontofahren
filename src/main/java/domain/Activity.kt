package domain

import javax.persistence.*
import java.io.Serializable

@Entity
class Activity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    companion object {

        private const val serialVersionUID = 1L
    }
}
