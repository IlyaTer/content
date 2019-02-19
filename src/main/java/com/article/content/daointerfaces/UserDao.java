package com.article.content.daointerfaces;

import com.article.content.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>{
   
    User findByLogin(String login);
}
