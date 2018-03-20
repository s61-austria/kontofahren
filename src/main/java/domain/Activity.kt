package domain

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "activity")
data class Activity(
    var name: String = "string"
) : Base(){

}
