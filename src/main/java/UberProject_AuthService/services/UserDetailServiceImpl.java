package UberProject_AuthService.services;

import UberProject_AuthService.helpers.AuthPassengerDetails;
import UberProject_AuthService.models.Passenger;
import UberProject_AuthService.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//This class is responsible for loading the user in the form of UserDetails object for auth

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()){
            System.out.println("Passenger is Present");
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find the Passenger by the given Email");
        }
    }
}
