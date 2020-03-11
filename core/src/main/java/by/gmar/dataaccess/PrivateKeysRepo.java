package by.gmar.dataaccess;

import by.gmar.model.user.PrivateCode;
import by.gmar.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author s.kosik
 */
public interface PrivateKeysRepo extends PagingAndSortingRepository<PrivateCode, Long>{
    PrivateCode findByUserAndCodeAndComprometation(final User user, final String code, final Boolean comprometation);
    PrivateCode findByCodeAndComprometation(String code, Boolean comprometation);
}
