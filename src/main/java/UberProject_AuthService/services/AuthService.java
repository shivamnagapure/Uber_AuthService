package UberProject_AuthService.services;

import UberProject_AuthService.dtos.PassengerSignUpResponseDto;
import UberProject_AuthService.dtos.PassengerSignupRequestDto;
import UberProject_AuthService.mapper.PassengerMapper;
import UberProject_AuthService.models.Passenger;
import UberProject_AuthService.repository.PassengerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerMapper passengerMapper = Mappers.getMapper(PassengerMapper.class);

    PassengerRepository passengerRepository ;

    public AuthService(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository ;
    }

    public PassengerSignUpResponseDto signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        System.out.println(passengerSignupRequestDto.getEmail() + "1");
        // map Request DTO → Entity
        Passenger passenger = passengerMapper.passengerSignUpReqToPassenger(passengerSignupRequestDto);

        System.out.println(passenger.getEmail() + "2");
        //save Entity
        Passenger savePassenger = passengerRepository.save(passenger) ;
        System.out.println(savePassenger.getEmail() + "3");

        //Map entity to response Dto
        return passengerMapper.passengerToResponseDto(savePassenger);

    }


}
