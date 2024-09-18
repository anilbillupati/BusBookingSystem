package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.UserDto;
import io.BookingSystem.BusBookingSystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User userDtoToUser(UserDto userDto);

	UserDto userToUserDto(User user);

}
