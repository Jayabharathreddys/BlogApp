package com.jaya.users;

import com.jaya.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserEntity createUser(CreateUserRequest req){
        var newUser = modelMapper.map(req,UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String userName){
        return usersRepository.findByUsername(userName)
                .orElseThrow(()->new UserNotFoundException(userName));
    }
    public UserEntity getUser(Long userId){
        return usersRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException(userId));
    }

    public UserEntity loginUser(CreateUserRequest req){
        var user= usersRepository.findByUsername(req.getUsername())
                .orElseThrow(()->new UserNotFoundException(req.getUsername()));
        var passMatch =  passwordEncoder.matches(req.getPassword(), user.getPassword());
        if(!passMatch)
            throw new InvalidCredentialsException();

        return user;
    }

     public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User" +username +" not found");
        }
        public UserNotFoundException(Long authorID){
            super("User" +authorID +" not found");
        }
    }

    public static class InvalidCredentialsException extends IllegalArgumentException{
        public InvalidCredentialsException(){
            super("Invalid creds entered");
        }
    }

}
