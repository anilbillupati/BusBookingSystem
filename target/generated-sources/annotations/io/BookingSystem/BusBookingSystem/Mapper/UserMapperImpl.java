package io.BookingSystem.BusBookingSystem.Mapper;

import io.BookingSystem.BusBookingSystem.dto.UserDto;
import io.BookingSystem.BusBookingSystem.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-18T15:15:33+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDto.getEmail() );
        user.setPassword( userDto.getPassword() );
        user.setRole( userDto.getRole() );
        user.setUserName( userDto.getUserName() );

        return user;
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setEmail( user.getEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setRole( user.getRole() );
        userDto.setUserName( user.getUserName() );

        return userDto;
    }
}
