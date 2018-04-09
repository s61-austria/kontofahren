package dao

import domain.Country
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CountryDao {

    @PersistenceContext
    lateinit var em: EntityManager

    fun persistCountry(country: Country): Country {
        em.persist(country)

        return country;
    }
}
