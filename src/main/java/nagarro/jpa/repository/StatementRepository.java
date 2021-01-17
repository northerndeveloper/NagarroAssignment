package nagarro.jpa.repository;

import nagarro.jpa.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author alperkopuz
 * JPA Repository class used for DB Operations of Statement
 */
public interface StatementRepository extends JpaRepository<Statement, Integer> {

    /**
     * Find Statements with the demanded Account ID
     *
     * @param accountId
     * @return
     */
    @Query("SELECT s FROM Statement s WHERE s.accountId = :accountId")
    List<Statement> findStatementsByAccountId(@Param("accountId") String accountId);

}
