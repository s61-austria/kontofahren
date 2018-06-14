package dao

import domain.Country
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class CountryDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun persistCountry(country: Country): Country {
        em.persist(country)

        return country
    }

    fun getCountryByUUID(countryId: String) = em
        .createQuery("SELECT c FROM Country c WHERE c.uuid = :countryId", Country::class.java)
        .setParameter("countryId", countryId)
        .singleResult
}
