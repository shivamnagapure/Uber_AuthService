package UberProject_AuthService.helpers;

import UberProject_AuthService.models.Passenger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Spring Security works with UserDetails objects when authenticating and authorizing users.
/*
    UserDetails is a polymorphic type because it allows Spring Security to treat many different user representations
    (User, AuthPassengerDetails, AdminDetails, etc.) in the same way through the UserDetails interface.
*/

@Setter
@Getter
public class AuthPassengerDetails implements UserDetails {

    private final Passenger passenger;

    public AuthPassengerDetails(Passenger passenger){
       this.passenger = passenger ;
    }

    @Override
    public String getPassword() {
        return passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_PASSENGER");
    }

    @Override
    public String getUsername() {
        return passenger.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}
