package exchange.calcul.service;

import org.springframework.stereotype.Service;

import exchange.calcul.domain.Country;
import exchange.calcul.repository.CountryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Long countrySave(Country country){
        countryRepository.save(country);
        return country.getId();
    }

    public Optional countryFindOne(Country country){
        return countryRepository.findById(country.getId());
    }
    
}
