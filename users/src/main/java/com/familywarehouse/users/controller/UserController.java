package com.familywarehouse.users.controller;

import com.familywarehouse.users.constants.UserConstants;
import com.familywarehouse.users.dto.ErrorResponseDto;
import com.familywarehouse.users.dto.ResponseDto;
import com.familywarehouse.users.dto.UserDto;
import com.familywarehouse.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create an user in Family-Warehouse",
            description = "REST API to create User"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return generateResponseEntity(HttpStatus.CREATED, UserConstants.MESSAGE_201);
    }

    @Operation(
            summary = "Fetch users in Family-Warehouse",
            description = "REST API Users"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status OK"
    )
    @GetMapping
    public ResponseEntity<Page<UserDto>> fetchUsers(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                    @RequestParam(required = false, defaultValue = "5") @Min(1) int size) {
        Page<UserDto> users = userService.findAll(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @Operation(
            summary = "Fetch an user in Family-Warehouse",
            description = "REST API to fetch User"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> fetchUser(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    @Operation(
            summary = "Update an user in Family-Warehouse",
            description = "REST API to update User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Request failed or there are some conflicts"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        if (!id.equals(userDto.getId())) {
            return generateResponseEntity(HttpStatus.BAD_REQUEST, UserConstants.MESSAGE_INCORRECT_USER_ID);
        }
        boolean isUpdated = userService.updateUser(userDto);
        if (isUpdated) {
            return generateResponseEntity(HttpStatus.OK, UserConstants.MESSAGE_200);
        } else {
            return generateResponseEntity(HttpStatus.CONFLICT, UserConstants.MESSAGE_409);
        }
    }

    @Operation(
            summary = "Delete an user in Family-Warehouse",
            description = "REST API to delete User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Request failed or there are some conflicts"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return generateResponseEntity(HttpStatus.OK, UserConstants.MESSAGE_200);
        } else {
            return generateResponseEntity(HttpStatus.CONFLICT, UserConstants.MESSAGE_409);
        }
    }

    private ResponseEntity<ResponseDto> generateResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ResponseDto(Integer.toString(httpStatus.value()), message));
    }
}
