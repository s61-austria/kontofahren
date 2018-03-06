package domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
}
