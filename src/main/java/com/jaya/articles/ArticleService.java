package com.jaya.articles;

import com.jaya.articles.dtos.CreateArticleRequest;
import com.jaya.users.UsersRepository;
import com.jaya.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    public ArticleService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }
    public Iterable<ArticleEntity> getAllArticles(){
        return articlesRepository.findAll();
    }
    public ArticleEntity getArticleBySlug(String slug){
        var article= articlesRepository.findBySlug(slug);
        if (article==null)
            throw new ArticleNotFoundException(slug);

        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId){
    var user = usersRepository.findById(authorId)
            .orElseThrow(()->new UsersService.UserNotFoundException(authorId));
        return articlesRepository.save(ArticleEntity.builder()
                .titile(a.getTitle())
                .body(a.getBody())
                .author(user)
                .build());
    }

    static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article "+slug+ " not found");
        }
    }
}
