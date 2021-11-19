package com.tutrit.tt.security.wiki.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Article {
    @Id
    Long articleId;
    String title;
    @Column(columnDefinition = "clob")
    String text;
}
