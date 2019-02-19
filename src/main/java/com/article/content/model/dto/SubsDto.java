package com.article.content.model.dto;


public class SubsDto {
    
    private Long id;
    private String user;
    private String subscriptions;

    public SubsDto() {
    }

    public SubsDto(Long id, String user, String subscriptions) {
        this.id = id;
        this.user = user;
        this.subscriptions = subscriptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }
    
    
    
}
