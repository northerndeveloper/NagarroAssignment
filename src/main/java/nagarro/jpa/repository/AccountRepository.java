package nagarro.jpa.repository;

import nagarro.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author alperkopuz
 * JPA Repository class used for DB Operations of Account
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
