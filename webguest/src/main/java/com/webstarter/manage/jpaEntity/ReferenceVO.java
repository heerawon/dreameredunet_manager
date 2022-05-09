package com.webstarter.manage.jpaEntity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Entity(name = "reference") //db Table명
@Builder
public class ReferenceVO {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  private String title;
  private String content;
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date regDt;
  private Long isDel;

  public ReferenceVO update(String title,String content){
    this.title = title;
    this.content = content;
    return this;
  }

  public ReferenceVO updateIsDel(Long isDel){
    this.isDel = isDel;
    return this;
  }



  public ReferenceVO toEntity(){
    return ReferenceVO.builder()
            .content(content)
            .title(title)
            .isDel(0L)
            .regDt(new Date())
            .build();
  }


}
