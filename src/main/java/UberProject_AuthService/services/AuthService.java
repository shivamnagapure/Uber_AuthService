package UberProject_AuthService.services;

import UberProject_AuthService.dtos.PassengerSignUpResponseDto;
import UberProject_AuthService.dtos.PassengerSignupRequestDto;
import UberProject_AuthService.mapper.PassengerMapper;
import UberProject_AuthService.models.Passenger;
import UberProject_AuthService.repository.PassengerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerMapper passengerMapper = Mappers.getMapper(PassengerMapper.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder ;
    PassengerRepository passengerRepository ;

    public AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passengerRepository = passengerRepository ;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder ;
    }

    public PassengerSignUpResponseDto signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        // map Request DTO → Entity
        Passenger passenger = passengerMapper.passengerSignUpReqToPassenger(passengerSignupRequestDto);

        //Hash password
        passenger.setPassword(bCryptPasswordEncoder.encode(passenger.getPassword()));

        //save Entity
        Passenger savePassenger = passengerRepository.save(passenger) ;

        //Map entity to response Dto
        return passengerMapper.passengerToResponseDto(savePassenger);

    }


}
