package UberProject_AuthService.filters;

import UberProject_AuthService.services.JwtService;
import UberProject_AuthService.services.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService ;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT validation for public endpoints
        if (path.startsWith("/api/v1/auth/signup") || path.startsWith("/api/v1/auth/signin")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null ;
        System.out.println("doFilterInternal");
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null){
            System.out.println("Token is null");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        System.out.println("Incoming Token " + token );

        String email = jwtService.extractEmail(token);

        System.out.println("Email " + email);

        if(email != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            //it validates Signature , Subject(email) and expiration time
            if(jwtService.validateToken(token , userDetails.getUsername())){
                // Creates an Authentication object and sets it in SecurityContext
                // to let Spring Security treat this request as authenticated.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null);
                // Add request-specific details (IP, session ID) to authentication object
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }

        // passes the request and response to the next filter in the chain.
        // if on filter is their then it send to controller
        System.out.println("Forwarding req");
        filterChain.doFilter(request, response);
    }
}
