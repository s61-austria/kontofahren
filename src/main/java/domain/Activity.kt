package domain

import javax.persistence.*
import java.io.Serializable
import java.util.*

@Entity
class Activity : Serializable {

    @Id
    private val id: String? = UUID.randomUUID().toString()

    constructor(){

    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
