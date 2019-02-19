package com.article.content.rest.client;

import com.article.content.rest.client.model.MailDataDto;
import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ArticleRestClient {

    public static void sendDataToSubs(MailDataDto data, String uri) {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(
                Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));

        RestTemplate rest = new RestTemplate();

        HttpEntity<MailDataDto> requestBody = new HttpEntity<>(data, headers);

        ResponseEntity<MailDataDto> response
                = rest.postForEntity(uri, requestBody, MailDataDto.class);

        System.out.println(response.getStatusCode());
    }//end sendDataToSubs

}
