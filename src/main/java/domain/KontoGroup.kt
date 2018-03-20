package domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class KontoGroup(
    @Id
    val groupname: String
) {
    @ManyToMany
    @JoinTable(
        name = "groups_users",
        joinColumns = [(JoinColumn(name = "groupname", referencedColumnName = "groupname"))],
        inverseJoinColumns = [(JoinColumn(name = "userName", referencedColumnName = "userName"))]
    )
    val users: MutableSet<KontoUser> = mutableSetOf()
}
