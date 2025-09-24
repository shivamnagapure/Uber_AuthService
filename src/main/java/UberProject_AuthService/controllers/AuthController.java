package UberProject_AuthService.controllers;

import UberProject_AuthService.dtos.AuthRequestDto;
import UberProject_AuthService.dtos.PassengerSignUpResponseDto;
import UberProject_AuthService.dtos.PassengerSignupRequestDto;
import UberProject_AuthService.services.AuthService;
import UberProject_AuthService.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;
    AuthService authService ;
    AuthenticationManager authenticationManager ;

    public AuthController(AuthService authService , AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService = authService ;
        this.authenticationManager = authenticationManager ;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerSignUpResponseDto responseDto = authService.signUpPassenger(passengerSignupRequestDto) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signin(@RequestBody AuthRequestDto authRequestDto , HttpServletResponse response){
        /*
             Authenticate the user by validating the provided email and password
             with Spring Security's AuthenticationManager. If the credentials are valid,
             an Authentication object containing the user's details and authorities is returned.
             Otherwise, an AuthenticationException (like BadCredentialsException) will be thrown.
         */
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail() , authRequestDto.getPassword())) ;

        if (authentication.isAuthenticated()){
            //Generate Token
            String token = jwtService.generateToken(authRequestDto.getEmail());

            //Create Cookie
            ResponseCookie cookie = ResponseCookie.from("jwt" , token)
                    .httpOnly(true) // prevent JS access
                    .secure(true)   //use only with HTTPS
                    .path("/")      //cookie is valid for all endpoints
                    .maxAge(24 * 60 * 60) //cookie expiration time
                    .sameSite("Strict")  // CSRF protection
                    .build();

            // 3. Add cookie to response header
            response.addHeader("Set-Cookie" , cookie.toString());

            return ResponseEntity.ok("Login successful");
        }
        else {
            System.out.println("VAA");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Inside validate controller");
        for(Cookie cookie: request.getCookies()) {
            System.out.println(cookie.getName() + " " + cookie.getValue());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
