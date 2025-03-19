package com.familywarehouse.users.service;

import com.familywarehouse.users.dto.UserDto;

public interface UserService {

    void createUser(UserDto userDto);

    UserDto getById(Long id);

    boolean updateUser(UserDto userDto);

    boolean deleteUser(Long id);
}
