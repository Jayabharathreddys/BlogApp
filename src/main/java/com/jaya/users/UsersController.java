package com.jaya.users;

import com.jaya.common.dtos.ErrorResponse;
import com.jaya.security.JWTService;
import com.jaya.users.dtos.CreateUserRequest;
import com.jaya.users.dtos.UserResponse;
import com.jaya.users.dtos.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;


    public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    String getUsers(){
        return "users";
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest req){
    UserEntity savedUser;
        savedUser = usersService.createUser(req);
        URI savedUserUri = URI.create("/users/"+savedUser.getId());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);

    userResponse.setToken(jwtService.createJwt(savedUser.getId()));

    return ResponseEntity.created(savedUserUri)
            .body(userResponse);
    }
    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest req){
        UserEntity savedUser = usersService.loginUser(
                new CreateUserRequest(req.getUsername(), req.getPassword(), "" ));
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.createJwt(savedUser.getId()));
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({UsersService.UserNotFoundException.class,
    UsersService.InvalidCredentialsException.class})
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception e){
        String msg;
        HttpStatus status;

        if(e instanceof UsersService.UserNotFoundException){
            msg= e.getMessage();
            status=HttpStatus.NOT_FOUND;
        }if(e instanceof UsersService.InvalidCredentialsException){
            msg= e.getMessage();
            status=HttpStatus.UNAUTHORIZED;
        }else{
            msg= "something went wrong";
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse internalServerError = ErrorResponse.builder()
                .message(msg)
                .build();

        return ResponseEntity.status(status).body(
                internalServerError
        );
    }
}
