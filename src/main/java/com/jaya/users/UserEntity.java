package com.jaya.users;

import com.jaya.articles.ArticleEntity;
import com.jaya.comments.CommentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Collection;

@Entity(name = "users")
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = true)
    private String bio;

    @Column(nullable = true)
    private String image;

    @OneToMany(mappedBy = "author")
    private Collection<ArticleEntity> articles;

    @OneToMany(mappedBy = "author")
    private Collection<CommentEntity> comments;

}
