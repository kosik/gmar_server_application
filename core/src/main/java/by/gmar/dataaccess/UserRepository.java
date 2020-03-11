package by.gmar.dataaccess;

import by.gmar.model.user.User;
import by.gmar.model.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author s.kosik
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(String username);

    @Query("select count(*) from ContentItem ci where owner=:user and "
            + "ci.contentType = by.gmar.model.content.ContentItemType.QA")
    long countQA(@Param("user")User user);
    
    @Query("select count(*) from Commentary where owner=:user")
    long countCommentaries(@Param("user")User user);
    
    @Query("select count(*) from ContentItem where owner=:user")
    long countArticles(@Param("user")User user);

    @Query("select u.friend from UserFriend u where u.owner = :user")
    List<User> findFriendsById(@Param("user")User user, Pageable pageable);
    
    @Query("select count(u.friend) > 0 from UserFriend u where u.owner = :user and u.friend = :friendId")
    boolean isItFriend(@Param("user")User user, @Param("friendId")User friendId);

    @Query("select u.owner from Invitation u where u.contact = :contact")
    List<User> findByContact(final @Param("contact")String contact);    
}