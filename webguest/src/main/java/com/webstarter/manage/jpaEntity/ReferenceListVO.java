package com.webstarter.manage.jpaEntity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface ReferenceListVO {
    Long getId();
    String getTitle();
    Date getRegDt();
}
