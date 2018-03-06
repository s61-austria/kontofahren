package domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    private val name: String? = null
}
