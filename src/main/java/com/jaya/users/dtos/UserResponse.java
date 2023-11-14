package com.jaya.users.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String image;
    private String token;

}
