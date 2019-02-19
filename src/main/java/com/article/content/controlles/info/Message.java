package com.article.content.controlles.info;

import java.io.Serializable;


public class Message implements Serializable{
    //0 - status Ok, 1 - satus error (error description is in description field)
    private Integer status;
    private String description;

    public Message() {
    }

    public Message(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
