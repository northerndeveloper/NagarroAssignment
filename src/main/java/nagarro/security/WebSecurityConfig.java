package nagarro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO acaba bu değerleri application.properties den mi alsan ???
    private static final String ADMIN_USERNAME = "testadmin";

    private static final String NORMAL_USERNAME = "testUser";

    private static final String ENCODED_ADMIN_PASSWORD = "$2a$10$ASVTlfCLOhUqtAbdskVDjOpfRnYGuQd7P95hzdjuZv4Jguk.cKloi";

    private static final String ENCODED_USER_PASSWORD = "$2a$10$seP4sR0yVkYK9mInW8eMqO33YDO.RN2r/xkwJgWz9HE5OCTvh4xEm";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        //TODO documentation
        http.sessionManagement().maximumSessions(2);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(ADMIN_USERNAME).password(ENCODED_ADMIN_PASSWORD).roles("ADMIN");

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(NORMAL_USERNAME).password(ENCODED_USER_PASSWORD).roles("USER");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
