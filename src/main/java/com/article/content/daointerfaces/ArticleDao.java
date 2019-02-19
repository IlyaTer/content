package com.article.content.daointerfaces;

import com.article.content.model.Article;
import org.springframework.data.repository.CrudRepository;


public interface ArticleDao extends CrudRepository<Article, Long>{
   
}
