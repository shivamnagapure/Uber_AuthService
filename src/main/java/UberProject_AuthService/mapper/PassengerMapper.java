package UberProject_AuthService.mapper;

import UberProject_AuthService.dtos.PassengerSignUpResponseDto;
import UberProject_AuthService.dtos.PassengerSignupRequestDto;
import UberProject_AuthService.models.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    Passenger passengerSignUpReqToPassenger(PassengerSignupRequestDto passengerSignupRequestDto);

    PassengerSignUpResponseDto passengerToResponseDto(Passenger passenger);

}
