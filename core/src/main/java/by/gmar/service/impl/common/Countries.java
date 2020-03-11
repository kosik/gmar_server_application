package by.gmar.service.impl.common;

import by.gmar.dataaccess.CountryRepo;
import by.gmar.model.user.Country;
import by.gmar.service.common.ICountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author s.kosik
 */
@Service
public class Countries implements ICountry {
    @Autowired
    private CountryRepo countryRepo;
    
    @Override
    public Country getByCountryName(final String country) {
        return countryRepo.findByCountryName(country);
    }
    
}
