package com.jaya.articles;

import com.jaya.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @GetMapping("")
    public String getArticles(){
        return "articles";
    }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable("id") String id){
        return "Get article with "+id;
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntity user){
        return "Article created by"+user.getUsername();

    }
}
