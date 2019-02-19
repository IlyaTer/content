package com.article.content.controlles.article;

import com.article.content.controlles.article.exceptions.classes.others.ArticleNotfoundException;
import com.article.content.controlles.info.Message;
import com.article.content.daointerfaces.ArticleDao;
import com.article.content.daointerfaces.CommentDao;
import com.article.content.daointerfaces.SubsDao;
import com.article.content.daointerfaces.UserDao;
import com.article.content.model.Article;
import com.article.content.model.Comment;
import com.article.content.model.Subs;
import com.article.content.model.User;
import com.article.content.model.dto.ArticleDto;
import com.article.content.model.dto.CommentDto;
import com.article.content.model.dto.UserDto;
import com.article.content.rest.client.ArticleRestClient;
import com.article.content.rest.client.model.MailDataDto;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/article")
@Transactional
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    
    @Autowired
    private SubsDao subsDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Value("${my.email.server}")
    private String emailServUri;

    //get controlles
    @CrossOrigin
    @RequestMapping(value = "/getAll", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getAll() {

        logger.info("Executes getAll");

        List<ArticleDto> articles = new LinkedList<>();

        for (Article art : articleDao.findAll()) {
            articles.add(new ArticleDto(art.getId(),
                    art.getName(), art.getTopic(),
                    null, art.getAuthor().getLogin(), null));
        }//end for

        return articles;
    }//end getAll

    @CrossOrigin
    @RequestMapping(value = "/articlesByUser/{user}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getArticleByUser(@PathVariable("user") String user) {
        List<ArticleDto> resList = new LinkedList<>();

        for (Article art : articleDao.findAll()) {
            if (art.getAuthor().getLogin().equals(user)) {
                resList.add(new ArticleDto(art.getId(), art.getName(),
                        art.getTopic(), null,
                        art.getAuthor().getLogin(), null));
            }
        }

        return resList;
    }//

    @CrossOrigin
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticleDto getArticle(@PathVariable("id") Long id) {

        List<CommentDto> comments = new LinkedList();
        ArticleDto article = new ArticleDto();

        try {
            Article articleIn = articleDao.findById(id).get();

            for (Comment c : commentDao.findByArticle(articleIn)) {
                comments.add(new CommentDto(c.getId(), c.getArticle().getId(),
                        c.getAuthor().getLogin(), c.getText()));
            }

            article.setId(id);
            article.setName(articleIn.getName());
            article.setAuthor(articleIn.getAuthor().getLogin());
            article.setTopic(articleIn.getTopic());
            article.setText(articleIn.getText());
            article.setComments(comments);

            return article;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ArticleNotfoundException();
        }
    }//end getArticle

    @CrossOrigin
    @RequestMapping(value = "/getByMatches/{matches}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getArticlesByMatches(@PathVariable("matches") String term) {
        List<ArticleDto> resList = new LinkedList<>();

        for (Article art : articleDao.findAll()) {
            if (art.getName().contains(term)) {
                resList.add(new ArticleDto(art.getId(), art.getName(),
                        art.getTopic(), null,
                        art.getAuthor().getLogin(), null));
            }
        }

        return resList;
    }

    //post(add) controllers
    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message addArticle(@RequestBody ArticleDto article) {
        Message message = new Message();
        User user = userDao.findByLogin(article.getAuthor());

        if (user == null) {
            message.setStatus(1);
            message.setDescription("No such user!");
            return message;
        }

        Article newArticle = new Article();
        newArticle.setAuthor(user);
        newArticle.setName(article.getName());
        newArticle.setTopic(article.getTopic());
        newArticle.setText(article.getText());

        articleDao.save(newArticle);

        List<Subs> sbs = subsDao.findBySubscriptions(user);
        UserDto[] resSubs = new UserDto[sbs.size()];

        for (int i = 0; i < sbs.size(); i++) {
            UserDto uDto = new UserDto();
            User uSb = sbs.get(i).getUser();

            uDto.setId(uSb.getId());
            uDto.setLogin(uSb.getLogin());

            resSubs[i] = uDto;
        }

        String subject = "New Article!";
        String content = "New " + article.getAuthor()
                + "'s article name: " + article.getName();
        MailDataDto mailData = new MailDataDto(resSubs, content, subject);
        try {
            ArticleRestClient.sendDataToSubs(mailData, this.emailServUri);
        } catch (Exception e) {
            System.out.println("Is Exception!");
            System.out.println(e.getMessage());
        }

        message.setStatus(0);

        return message;
    }//end addArticle

    //put(update) controllers
    @CrossOrigin
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message updateArticle(@RequestBody ArticleDto article) {

        Message message = new Message();

        try {
            Article updateArticle = articleDao.findById(article.getId()).get();
            updateArticle.setText(article.getText());
            updateArticle.setName(article.getName());
            updateArticle.setTopic(article.getTopic());
            articleDao.save(updateArticle);
            message.setStatus(0);

            return message;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ArticleNotfoundException();
        }
    }//end updateArticle

    //delete controllers
    @CrossOrigin
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message delArticle(@PathVariable("id") Long id) {
        Message message = new Message();
        try {
            Article article = articleDao.findById(id).get();
            articleDao.delete(article);
            message.setStatus(0);

            return message;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ArticleNotfoundException();
        }
    }//end delArticle

}//end ArticleController
