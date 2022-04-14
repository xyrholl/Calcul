package exchange.calcul.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import exchange.calcul.domain.Country;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CountryRepository {

    private final EntityManager em;

    public void save(Country country){
        em.persist(country);
    }

    public List<Country> findAll(){
        return em.createQuery("select c from Country c", Country.class).getResultList();
    }

    public Country findOne(Country country) {
        return em.find(Country.class, country.getId());
    }

}
