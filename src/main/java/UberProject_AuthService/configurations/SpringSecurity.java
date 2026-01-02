package UberProject_AuthService.configurations;

import UberProject_AuthService.filters.JwtAuthFilter;
import UberProject_AuthService.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration //Spring will treat this class as a source of bean definitions ,
public class SpringSecurity {

    @Autowired
    private UserDetailServiceImpl userDetailService ;

    @Autowired
    private JwtAuthFilter jwtAuthFilter ;

    //Spring Boot autoconfigures default security beans if you don’t provide your own
    //You don’t have to write or inject them manually for basic security to work.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
           .csrf(AbstractHttpConfigurer::disable)
           .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/auth/signup/**").permitAll()
                    .requestMatchers("/api/v1/auth/signin/**").permitAll()
                    .requestMatchers("/api/v1/auth/validate").authenticated()
                    .anyRequest().authenticated()
            )
            // Add your JWT filter before UsernamePasswordAuthenticationFilter
                /*
                    JWT Filter should check for tokens first
                    If a valid JWT exists, the user is already authenticated
                    Only if no valid JWT is found, fall back to username/password authentication
                 */
            .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
        System.out.println("filterChain");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        System.out.println("authenticationManager");
        //Build Authentication Manager , and configure it (means, tells it where to get user data , how to encode password , which authenticationProvider to use)
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailService) //
                .passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
