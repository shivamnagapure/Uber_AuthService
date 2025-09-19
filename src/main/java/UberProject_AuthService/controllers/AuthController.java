package UberProject_AuthService.controllers;

import UberProject_AuthService.dtos.PassengerSignUpResponseDto;
import UberProject_AuthService.dtos.PassengerSignupRequestDto;
import UberProject_AuthService.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    AuthService authService ;

    public AuthController(AuthService authService){
        this.authService = authService ;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerSignUpResponseDto responseDto = authService.signUpPassenger(passengerSignupRequestDto) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
