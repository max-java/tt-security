package com.tutrit.tt.security.staff.bean;

import com.tutrit.tt.security.staff.securityconfig.acl.AclAble;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person implements AclAble {
    @Id
    Long personId;
    Long cardId;
    @Column(columnDefinition = "boolean default false")
    Boolean inOffice;
    String fullName;

    public Long getId() {
        return this.personId;
    }
}
