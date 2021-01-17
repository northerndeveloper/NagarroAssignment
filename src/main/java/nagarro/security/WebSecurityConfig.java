package nagarro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author alperkopuz
 * Spring Security Class used for security managements such like authentication / authorization and adjustment of
 * concurrent session
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_USERNAME = "testadmin";

    private static final String NORMAL_USERNAME = "testUser";

    private static final String ENCODED_ADMIN_PASSWORD = "$2a$10$ASVTlfCLOhUqtAbdskVDjOpfRnYGuQd7P95hzdjuZv4Jguk.cKloi";

    private static final String ENCODED_USER_PASSWORD = "$2a$10$seP4sR0yVkYK9mInW8eMqO33YDO.RN2r/xkwJgWz9HE5OCTvh4xEm";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    /**
     * Method secures the application and makes user to login first in order to query for the statements also adjusts
     * concurrent session mechanism
     *
     * @param http
     * @throws Exception
     */
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

        /**
         * Concurrent session management handled with bu that method
         */
        http.sessionManagement().maximumSessions(2);
    }

    /**
     * Adjusts username and password provided in the assignment with the ADMIN and USER roles according to the users
     * Username and Password are sent by encoding.They are not sent without encoding.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(ADMIN_USERNAME).password(ENCODED_ADMIN_PASSWORD).roles(ADMIN);

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser(NORMAL_USERNAME).password(ENCODED_USER_PASSWORD).roles(USER);

    }

    /**
     * BCryptPasswordEncoder is a strong encoder class developped by Spring Framework
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
