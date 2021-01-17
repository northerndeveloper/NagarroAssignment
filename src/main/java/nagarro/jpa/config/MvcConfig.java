package nagarro.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author alperkopuz
 * The configuration class used for Spring MVC configurations for setting view names and
 * manages auto redirections
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Setting views and managing auto redirections
     *
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("statementinvestigation");
        registry.addViewController("/login").setViewName("login");

        registry.addRedirectViewController("/", "statementinvestigation");
        registry.addRedirectViewController("", "statementinvestigation");
    }

}
