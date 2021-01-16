package nagarro.jpa.repository;

import nagarro.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author alperkopuz
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
