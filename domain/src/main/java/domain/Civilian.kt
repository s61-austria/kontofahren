package domain

import exceptions.KontoException

import javax.persistence.*
import java.io.Serializable

@Entity
@DiscriminatorValue("Civilian")
class Civilian : User(), Serializable
