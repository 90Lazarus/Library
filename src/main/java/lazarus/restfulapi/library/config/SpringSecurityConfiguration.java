package lazarus.restfulapi.library.config;

import lazarus.restfulapi.library.service.LibraryUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new LibraryUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests()
                //swagger
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                //users & rented
                .antMatchers("/register", "/register/").anonymous()
                .antMatchers(HttpMethod.GET, "/users", "/users/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/users/*/rented/*").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/users/*").hasAnyAuthority("ADMIN") //only admin can set roles
                .antMatchers(HttpMethod.PUT, "/users/*/rented/*").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                //libraries
                .antMatchers(HttpMethod.GET, "/libraries**").authenticated()
                .antMatchers(HttpMethod.POST, "/libraries**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/libraries/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/libraries/**").hasAnyAuthority("ADMIN")
                //books
                .antMatchers(HttpMethod.GET, "/books**").authenticated()
                .antMatchers(HttpMethod.POST, "/books**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/books/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/books/**").hasAnyAuthority("ADMIN")
                //authors
                .antMatchers(HttpMethod.GET, "/authors**").authenticated()
                .antMatchers(HttpMethod.POST, "/authors**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/authors/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/authors/**").hasAnyAuthority("ADMIN")
                //genres
                .antMatchers(HttpMethod.GET, "/genres**").authenticated()
                .antMatchers(HttpMethod.POST, "/genres**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/genres/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/genres/**").hasAnyAuthority("ADMIN")
                //languages
                .antMatchers(HttpMethod.GET, "/languages**").authenticated()
                .antMatchers(HttpMethod.POST, "/languages**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/languages/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/languages/**").hasAnyAuthority("ADMIN")
                //publishers
                .antMatchers(HttpMethod.GET, "/publishers**").authenticated()
                .antMatchers(HttpMethod.POST, "/publishers**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/publishers/**").hasAnyAuthority("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/publishers/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();
        return httpSecurity.build();
    }
}