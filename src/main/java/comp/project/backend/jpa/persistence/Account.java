package comp.project.backend.jpa.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author alperkopuz
 */
@Entity
@Data
@Table(name = "Account")
@EqualsAndHashCode()
public class Account {

    @Id
    @Column(name = "ID")
    @JsonProperty("Account ID")
    private int id;

    @NotNull
    @Column(name = "account_number")
    @JsonProperty("Account Number")
    private String accountNumber;

    @NotNull
    @Column(name = "account_type")
    @JsonProperty("Account Type")
    private String accountType;

}
