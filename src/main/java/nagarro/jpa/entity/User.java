package nagarro.jpa.entity;

import lombok.Data;

@Data
public class User { //TODO

    private String username;
    private String password;
    private String[] roles;

    public User(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
