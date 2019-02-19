package com.article.content.controlles.user;

import com.article.content.controlles.info.Message;
import com.article.content.daointerfaces.ArticleDao;
import com.article.content.daointerfaces.CommentDao;
import com.article.content.daointerfaces.UserDao;
import com.article.content.model.User;
import com.article.content.model.dto.UserDto;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Transactional
public class UserController {

    @Autowired
    private UserDao userDao;

    //add controllers
    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message addUser(@RequestBody UserDto user) {
        Message message = new Message();
        User newUser = userDao.findByLogin(user.getLogin());

        if (newUser != null) {
            message.setStatus(1);
            message.setDescription("This user has added aldedy!");
            return message;
        }

        newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());

        userDao.save(newUser);

        message.setStatus(0);

        return message;
    }//end add User

    @CrossOrigin
    @RequestMapping(value = "/getAll", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> addUser() {
        List<UserDto> users = new LinkedList<>();

        for (User u : userDao.findAll()) {
            users.add(new UserDto(-1L, u.getLogin(), ""));
        }

        return users;
    }//end getAll 

    //.matches("[a-zA-Z]+")
}
