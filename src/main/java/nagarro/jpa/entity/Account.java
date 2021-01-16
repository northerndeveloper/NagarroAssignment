package nagarro.jpa.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author alperkopuz
 */
@Entity
@Data
@Table(name = "Account")
@EqualsAndHashCode
public class Account {

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "ID")
    private List<Statement> statements;

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
