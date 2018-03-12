package domain

import java.util.*
import javax.persistence.*

@Entity
@DiscriminatorValue("civilServant")
class CivilServant : KontoUser() {

    @Id
    private val id: String = UUID.randomUUID().toString()
}
