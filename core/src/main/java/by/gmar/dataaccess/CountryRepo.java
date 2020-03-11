package by.gmar.dataaccess;

import by.gmar.model.user.Country;
import by.gmar.model.user.Country;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author s.kosik
 */
public interface CountryRepo extends PagingAndSortingRepository<Country, Long>{
    Country findByPhoneCode(final String phoneCode);
    Country findByCountryTag(final String countryTag);
    Country findByCountryName(final String countryName);
    List<Country> findByLanguageTag(final String langTag);
}
