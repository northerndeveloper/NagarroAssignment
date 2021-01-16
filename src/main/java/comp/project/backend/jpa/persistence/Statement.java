package comp.project.backend.jpa.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Statement")
@EqualsAndHashCode
public class Statement {

    @Id
    @Column(name = "ID")
    @JsonProperty("ID")
    private int id;

    @NotNull
    @Column(name = "amount")
    @JsonProperty("Amount")
    private String amount;

    @Column(name = "datefield")
    @JsonProperty("DateField")
    @NotNull
    private String dateField;

    @Column(name = "account_id")
    @JsonProperty("Account ID")
    @NotNull
    private String accountId;


}
