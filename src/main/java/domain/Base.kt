package domain

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

abstract class Base {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

}
