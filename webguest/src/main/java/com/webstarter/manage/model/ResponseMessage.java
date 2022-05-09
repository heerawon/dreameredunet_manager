package com.webstarter.manage.model;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

@Getter
public class ResponseMessage {
    private Integer status;
    private HttpMessageModel httpMessageModel;
    private String message;
    private Object data;


    public ResponseMessage(Integer status, String message, Object data){
        this.message = message;
        this.status = status;
        this.data = data;
    }


    public ResponseEntity<HttpMessageModel> getResponse(){
        httpMessageModel = new HttpMessageModel();
        httpMessageModel.setMessage(message);
        httpMessageModel.setData(data);
        httpMessageModel.setStatus(status);
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<HttpMessageModel>(httpMessageModel,headers,status);
    }

    public enum StatusEnum {
        OK(200, "OK"),
        BAD_REQUEST(400, "BAD_REQUEST"),
        NOT_FOUND(404, "NOT_FOUND"),
        INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

        int statusCode;
        String code;

        StatusEnum(int statusCode, String code) {
            this.statusCode = statusCode;
            this.code = code;
        }
    }



}


