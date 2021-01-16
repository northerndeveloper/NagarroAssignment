package nagarro.jpa.repository;

import nagarro.jpa.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Integer> {

    @Query("SELECT s FROM Statement s WHERE s.accountId = :accountId")
    List<Statement> findStatementsByAccountId(@Param("accountId") String accountId);

}
