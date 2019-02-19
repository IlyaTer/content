package com.article.content.controlles.comment;

import com.article.content.controlles.article.exceptions.classes.others.ArticleNotfoundException;
import com.article.content.controlles.comment.exceptions.others.NoSuchCommentException;
import com.article.content.controlles.info.Message;
import com.article.content.daointerfaces.ArticleDao;
import com.article.content.daointerfaces.CommentDao;
import com.article.content.daointerfaces.UserDao;
import com.article.content.model.Article;
import com.article.content.model.Comment;
import com.article.content.model.User;
import com.article.content.model.dto.CommentDto;
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
@RequestMapping(value = "/comment")
@Transactional
public class CommentControllers {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;
    
    

    //post(add) controllers
    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message addComment(@RequestBody CommentDto comment) {
        Message message = new Message();
        try {
            Article article = articleDao.findById(comment.getArticle()).get();
            User user = userDao.findByLogin(comment.getAuthor());
            Comment newComment = new Comment();

            if (user == null) {
                message.setStatus(1);
                message.setDescription("No such user!");
                return message;
            }

            newComment.setArticle(article);
            newComment.setAuthor(user);
            newComment.setText(comment.getText());

            commentDao.save(newComment);

            message.setStatus(0);
            message.setDescription(newComment.getId().toString());

            return message;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ArticleNotfoundException();
        }
    }//end addComment
    
    
    //delete controllers
    @CrossOrigin
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message delComment(@PathVariable("id") Long id) {
        Message message = new Message();
        try {
            Comment delComment = commentDao.findById(id).get();
            commentDao.delete(delComment);
            message.setStatus(0);
            
            return message;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NoSuchCommentException();
        }
    }//end delComment
    
}
