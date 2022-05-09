package com.webstarter.manage.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UploadFile {
    private Integer id;
    private String url;
    private String fileName;
    private String contentType;
    private String cDate;


    public UploadFile(String url, String fileName, String contentType) {
        this.url = url;
        this.fileName = fileName;
        this.contentType = contentType;
    }
    public UploadFile(Integer id,String url, String fileName, String contentType) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.contentType = contentType;
    }


}



