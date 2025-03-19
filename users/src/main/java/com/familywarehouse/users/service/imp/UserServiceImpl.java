package com.familywarehouse.users.service.imp;

import com.familywarehouse.users.dto.UserDto;
import com.familywarehouse.users.entity.User;
import com.familywarehouse.users.exception.ResourceNotFoundException;
import com.familywarehouse.users.exception.UserAlreadyExistsException;
import com.familywarehouse.users.mapper.UserMapper;
import com.familywarehouse.users.repository.UserRepository;
import com.familywarehouse.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

//    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
        String email = userDto.getEmail();
        Optional<User> existedUser = userRepository.findByEmail(email);
        if (existedUser.isPresent()) {
            throw new UserAlreadyExistsException("User already registered with given email: " + email);
        }
        User toSave = UserMapper.toUser(userDto);
//        toSave.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(toSave);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));

        return UserMapper.toDto(user);
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        boolean isUpdated = false;
        if (userDto != null) {
            Long id = userDto.getId();
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
            userRepository.save(UserMapper.toUser(userDto, user));
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
        userRepository.deleteById(id);
        return true;
    }
}
