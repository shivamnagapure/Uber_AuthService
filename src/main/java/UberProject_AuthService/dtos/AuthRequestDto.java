package UberProject_AuthService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String email;
    private String password;
}