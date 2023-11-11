package com.jaya.users;

import com.jaya.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UserEntity createUser(CreateUserRequest req){
        var newUser = modelMapper.map(req,UserEntity.class);

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
        return usersRepository.findByUsername(req.getUsername())
                .orElseThrow(()->new UserNotFoundException(req.getUsername()));
       // return user;
    }

     public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User" +username +" not found");
        }
        public UserNotFoundException(Long authorID){
            super("User" +authorID +" not found");
        }
    }

}
