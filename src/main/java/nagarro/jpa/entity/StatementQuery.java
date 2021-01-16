package nagarro.jpa.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StatementQuery {

    public String accountId;

    public Date fromDate;

    public Date toDate;

    public BigDecimal fromBalance;

    public BigDecimal toBalance;


}
