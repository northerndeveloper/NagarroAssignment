package comp.project.backend.jpa.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author alperkopuz
 */
public interface AccountRepository extends JpaRepository<Account, String> {

}
