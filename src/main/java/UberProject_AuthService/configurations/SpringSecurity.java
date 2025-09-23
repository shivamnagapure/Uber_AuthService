package UberProject_AuthService.configurations;

import UberProject_AuthService.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Spring will treat this class as a source of bean definitions ,
public class SpringSecurity {

    @Autowired
    private UserDetailServiceImpl userDetailService ;

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
                .anyRequest().authenticated()
           );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        System.out.println("Done");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:5173") // frontend origin
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true); // needed for HttpOnly cookie
            }
        };
    }
}
