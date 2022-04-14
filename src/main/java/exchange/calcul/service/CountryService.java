package exchange.calcul.service;

import org.springframework.stereotype.Service;

import exchange.calcul.domain.Country;
import exchange.calcul.repository.CountryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Long countrySave(Country country){
        countryRepository.save(country);
        return country.getId();
    }

    public Country countryFindOne(Country country){
        return countryRepository.findOne(country);
    }
    
}
