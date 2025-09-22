package UberProject_AuthService.services;

import UberProject_AuthService.models.Booking;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {
    // have to get it from application.properties
    @Value("${jwt.expiration}")
    private Long expiration ;

    @Value("${jwt.secret}")
    private  String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate token with custom claims
    public String generateToken(Map<String , Object> payload ,  String email){
        return Jwts.builder()
                .setClaims(payload)
                .setSubject(email) //identify the user uniquely , we pass a property which uniquely identify the user
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

    //return true if token expired
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // Validate token
    public boolean validateToken(String token , String email){
        //get Subject(email) from token , which uniquely identify user
        String tokenEmail = extractEmail(token);

        // Check if the token's username matches the given username
        // AND the token is not expired
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }

     public Object extractPayload(String token , String payload){
        Claims claims = extractAllClaims(token);
        return claims.get(payload);
     }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "a@b.com");
        mp.put("phoneNumber", "9999999999");
        String result = generateToken(mp , "a@b.com");
        System.out.println("Generated token is: " + result);
        System.out.println(extractPayload(result, "email").toString());
    }
}
