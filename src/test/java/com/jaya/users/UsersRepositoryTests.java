package com.jaya.users;

import com.jaya.articles.ArticleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Order(1)
    void can_create_users(){
        var user = UserEntity.builder()
                .username("jaya")
                .email("jb@blog.com")
                .build();
        usersRepository.save(user);
    }

    @Test
    @Order(2)
    void can_find_users(){
        var user = UserEntity.builder()
                .username("jaya")
                .email("jb@blog.com")
                .build();
        usersRepository.save(user);
        var users = usersRepository.findAll();
        Assertions.assertEquals(1, users.size());
    }
}
