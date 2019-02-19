package com.article.content.daointerfaces;


import com.article.content.model.Article;
import com.article.content.model.Comment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CommentDao extends CrudRepository<Comment, Long>{
  
    List<Comment> findByArticle(Article article);
    
    void removeByArticleId(Long id);
}
