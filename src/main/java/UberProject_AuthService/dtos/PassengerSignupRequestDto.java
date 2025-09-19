package UberProject_AuthService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerSignupRequestDto {

    private String name ;
    private String email ;
    private String password ;
    private String phoneNumber ;

}

