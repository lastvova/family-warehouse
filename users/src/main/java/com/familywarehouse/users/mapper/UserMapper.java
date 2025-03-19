package com.familywarehouse.users.mapper;

import com.familywarehouse.users.dto.UserDto;
import com.familywarehouse.users.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static User toUser(UserDto userDto, User user) {
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
