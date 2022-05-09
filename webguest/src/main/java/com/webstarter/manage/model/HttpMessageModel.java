package com.webstarter.manage.model;


import lombok.Data;

@Data
public class HttpMessageModel {
    private Integer status;
    private String message;
    private Object data;

    public HttpMessageModel() {
        this.status = null;
        this.data = null;
        this.message = null;
    }

}

