package domain

import java.util.* // ktlint-disable no-wildcard-imports
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@DiscriminatorValue("civilServant")
class CivilServant : KontoUser() {

    @Id
    private val id: String = UUID.randomUUID().toString()
}
