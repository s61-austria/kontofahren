package domain

import com.fasterxml.jackson.annotation.JsonIgnore
import utils.Open
import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "kontogroup")
@Open
data class KontoGroup(
    val groupName: String
) : Serializable {
    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = [(JoinColumn(name = "groupName", referencedColumnName = "groupName"))],
        inverseJoinColumns = [(JoinColumn(name = "userName", referencedColumnName = "userName"))]
    )
    @JsonIgnore
    val users = mutableSetOf<KontoUser>()
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(unique = true)
    var uuid: String = UUID.randomUUID().toString()
}
