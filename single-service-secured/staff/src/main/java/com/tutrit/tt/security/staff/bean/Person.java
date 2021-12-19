package com.tutrit.tt.security.staff.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    Long personId;
    Long cardId;
    @Column(columnDefinition = "boolean default false")
    Boolean inOffice;
    String fullName;
}
