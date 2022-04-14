package exchange.calcul;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import exchange.calcul.domain.Country;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
	public void init(){
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void init(){

            Country country0 = new Country();
            country0.setName("한국");
            country0.setCode("KRW");
            em.persist(country0);
    
            Country country1 = new Country();
            country1.setName("일본");
            country1.setCode("JPY");
            em.persist(country1);
    
            Country country3 = new Country();
            country3.setName("필리핀");
            country3.setCode("PHP");
            em.persist(country3);

            Country country4 = new Country();
            country4.setName("미국");
            country4.setCode("USD");
            em.persist(country4);

        }


    }
    
}
