package nagarro.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
