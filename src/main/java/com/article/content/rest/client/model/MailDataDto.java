package com.article.content.rest.client.model;

import com.article.content.model.dto.UserDto;



public class MailDataDto {
    
    private UserDto[] users;
    private String messageContent;
    private String messageSubject;

    public MailDataDto() {
    }

    public MailDataDto(UserDto[] users, String messageContent, String messageSubject) {
        this.users = users;
        this.messageContent = messageContent;
        this.messageSubject = messageSubject;
    }

    public UserDto[] getUsers() {
        return users;
    }

    public void setUsers(UserDto[] users) {
        this.users = users;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }
    
}//end MailDataDto
