package com.article.content.daointerfaces;

import com.article.content.model.Subs;
import com.article.content.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;



public interface SubsDao extends CrudRepository<Subs, Long> {
    
    List<Subs> findByUser(User user);
    
    List<Subs> findBySubscriptions(User subscriptions);
    
    Subs findByUserAndSubscriptions(User user, User subscriptions);
}
