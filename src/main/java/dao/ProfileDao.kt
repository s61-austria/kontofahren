package dao

import domain.Profile

import javax.ejb.Stateless

import javax.persistence.EntityManager

import javax.persistence.PersistenceContext

@Stateless

class ProfileDao {

    @PersistenceContext

    lateinit var em: EntityManager

    fun getProfileByUuid(uuid: String): Profile? = em
        .createQuery("SELECT p FROM Profile p WHERE p.uuid = :uuid", Profile::class.java)
        .setParameter("uuid", uuid)
        .singleResult

    fun allProfiles(): MutableList<Profile> = em.createQuery("SELECT p FROM Profile p", Profile::class.java).resultList

}
