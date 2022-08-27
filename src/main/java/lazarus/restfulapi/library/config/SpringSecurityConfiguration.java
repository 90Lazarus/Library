package lazarus.restfulapi.library.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.service.LibraryUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

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

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests()
                //swagger
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                //actuator
                .antMatchers("/actuator/**").permitAll()
                //users & rented
                .antMatchers("/register", "/register/").anonymous()
                .antMatchers("/forgot_password", "/forgot_password/").anonymous()
                .antMatchers("/reset_password**").anonymous()
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
                .and().httpBasic()
                //exceptions
                .authenticationEntryPoint(((request, response, authException) -> {
                    ErrorInfo errorInfo = ErrorInfo.builder()
                            .errorType(ErrorInfo.ErrorType.AUTHENTICATION)
                            .resourceType(ErrorInfo.ResourceType.ACCESS)
                            .message("Failed to authenticate user. Incorrect username and/or password!")
                            .build();
                    response.setContentType("application/json;charset=UTF-8");
                    response.setHeader("WWW-Authenticate", "Basic");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(errorInfo));
                }))
                .and()
                .exceptionHandling()
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    String message = "User " + (auth != null ? auth.getName() : "'unknown'") +
                            " attempted to access the protected URL: " + request.getRequestURI();
                    ErrorInfo errorInfo = ErrorInfo.builder()
                            .errorType(ErrorInfo.ErrorType.UNAUTHORIZED)
                            .resourceType(ErrorInfo.ResourceType.ACCESS)
                            .message(message)
                            .build();
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(objectMapper.writeValueAsString(errorInfo));
                }))
                .and()
                .formLogin();
        return httpSecurity.build();
    }
}