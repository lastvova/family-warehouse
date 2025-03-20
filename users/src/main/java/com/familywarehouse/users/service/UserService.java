package com.familywarehouse.users.service;

import com.familywarehouse.users.dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDto> findAll(int page, int size);

    void createUser(UserDto userDto);

    UserDto getById(Long id);

    boolean updateUser(UserDto userDto);

    boolean deleteUser(Long id);
}
