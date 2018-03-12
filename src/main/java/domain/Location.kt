package domain

import javax.persistence.*

@Entity
class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

    @ManyToOne
    private val country: Country? = null
}
