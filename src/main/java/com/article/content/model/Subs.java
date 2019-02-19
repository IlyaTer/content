package com.article.content.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Subs")
public class Subs {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "USERSB")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION")
    private User subscriptions;

    public Subs() {
    }

    public Subs(User user, User subscriptions) {
        this.user = user;
        this.subscriptions = subscriptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(User subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "Subs{" + "id=" + id + ", user=" + user + ", subscriptions=" + subscriptions + '}';
    }

}//end Subs
