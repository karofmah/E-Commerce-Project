/**

 The SecurityConfig class is used to configure Spring Security settings for the application.
 */
package no.ntnu.ecomback.security;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures Spring Security to allow certain HTTP requests without authentication, while requiring
     * authentication for all others.
     *
     * @param http the HttpSecurity object to configure
     * @return a SecurityFilterChain object representing the security filter chain
     * @throws Exception if an exception occurs while configuring the security settings
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests()
            .requestMatchers("/token").permitAll()
            .requestMatchers("/api/users/register").permitAll()
            .requestMatchers("/api/items/getItems").permitAll()
            .requestMatchers("/api/items/get/category").permitAll()
            .requestMatchers("/api/items/{id}").permitAll()
            .requestMatchers("/api/categories/getCategories").permitAll()
            .requestMatchers("/api/items/get/keyword").permitAll()
            .anyRequest().authenticated().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}