package nagarro.jpa.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class StatementQuery {

    @NotBlank(message = "Account ID is mandatory")
    public String accountId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date toDate;

    public BigDecimal fromBalance;

    public BigDecimal toBalance;


}
