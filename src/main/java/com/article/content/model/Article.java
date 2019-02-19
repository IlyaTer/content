package com.article.content.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Articles")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "CONTENT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Article() {
    }

    public Article(String name, String topic, String text, User autor) {
        this.name = name;
        this.topic = topic;
        this.text = text;
        this.author = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", name=" + name + ", topic=" + topic + ", text=" + text + ", author=" + author + '}';
    }

}//end Article
