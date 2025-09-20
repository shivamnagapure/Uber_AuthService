package UberProject_AuthService.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // have to get it from application.properties
    @Value("${jwt.secret}")
    private long expiration ;

    @Value("${}jwt.expiration")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate token with custom claims
    public String generateToken(Map<String , Object> payload ,  String email){
        return Jwts.builder()
                .setClaims(payload)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey()) // creates the signature ->  using header , payload and key
                .compact();
    }

    // Generate token with default claims
    public String generateToken(String email){
        return generateToken(new HashMap<>() , email);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract Expiration date from token
    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    //Extract Email from token
    public String extractEmail(String token){
        return extractClaim(token , Claims::getSubject);
    }

    // Validate token
    
}
