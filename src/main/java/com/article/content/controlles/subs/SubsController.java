package com.article.content.controlles.subs;

import com.article.content.controlles.info.Message;
import com.article.content.controlles.user.exceptions.others.NoSuchUserException;
import com.article.content.daointerfaces.SubsDao;
import com.article.content.daointerfaces.UserDao;
import com.article.content.model.Subs;
import com.article.content.model.User;
import com.article.content.model.dto.SubsDto;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subs")
@Transactional
public class SubsController {

    @Autowired
    private SubsDao subsDao;

    @Autowired
    private UserDao userDao;

    //get controllers
    @CrossOrigin
    @RequestMapping(value = "/getByUser/{user}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubsDto> getSubsByUser(@PathVariable("user") String login) {
        List<SubsDto> subs = new LinkedList<>();

        User resUser = userDao.findByLogin(login);

        if (resUser != null) {
            for (Subs s : subsDao.findByUser(resUser)) {
                subs.add(
                        new SubsDto(s.getId(), s.getUser().getLogin(),
                                s.getSubscriptions().getLogin()));
            }

            return subs;
        } else {
            throw new NoSuchUserException();
        }
    }//end getByUser

    @CrossOrigin
    @RequestMapping(value = "/getOfUser/{user}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubsDto> getSubsOfUser(@PathVariable("user") String login) {
        List<SubsDto> subs = new LinkedList<>();

        User resUser = userDao.findByLogin(login);

        if (resUser != null) {
            for (Subs s : subsDao.findBySubscriptions(resUser)) {
                subs.add(
                        new SubsDto(s.getId(), s.getUser().getLogin(),
                                s.getSubscriptions().getLogin()));
            }

            return subs;
        } else {
            throw new NoSuchUserException();
        }
    }//end getOfUser

    @CrossOrigin
    @RequestMapping(value = "/getConcret/{user}/{subscription}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SubsDto getConcret(@PathVariable("user") String login,
            @PathVariable("subscription") String subscription) {
        SubsDto subs = new SubsDto();

        User resUser = userDao.findByLogin(login);
        User resSbOfUser = userDao.findByLogin(subscription);

        if (resUser != null && resSbOfUser != null) {
            try {
                Subs subsIn = subsDao
                        .findByUserAndSubscriptions(resUser, resSbOfUser);
                if(subsIn == null){
                    return subs;
                }
                subs.setId(subsIn.getId());
                subs.setUser(login);
                subs.setSubscriptions(subscription);

                return subs;
            } catch (NoSuchElementException e) {
                return subs;
            }
        } else {
            throw new NoSuchUserException();
        }
    }//end getOfUser

    
    
    //post(add) controllers
    @CrossOrigin
    @RequestMapping(value = "/addSubs", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message addSubs(@RequestBody SubsDto subs) {

        Message message = new Message();
        try {
            User resUser = userDao.findByLogin(subs.getUser());
            User resSbOfUser = userDao.findByLogin(subs.getSubscriptions());
            
            Subs newSubs = new Subs();
            
            newSubs.setUser(resUser);
            newSubs.setSubscriptions(resSbOfUser);
            
            subsDao.save(newSubs);
            message.setStatus(0);
            
            return message;            
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchUserException();
        }
    }//end addSubs

    
    //delete controllers
    @CrossOrigin
    @RequestMapping(value = "/delSubs/{user}/{subscription}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message delSubs(@PathVariable("user") String login,
            @PathVariable("subscription") String subscription) {

        Message message = new Message();
        try {
            User resUser = userDao.findByLogin(login);
            User resSbOfUser = userDao.findByLogin(subscription);
            
            Subs delSubs = subsDao.findByUserAndSubscriptions(resUser, resSbOfUser);
            
            subsDao.delete(delSubs);
            message.setStatus(0);
            
            return message;            
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchUserException();
        }
    }//end addSubs
    
    
}//end SubsController
