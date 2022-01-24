package com.tutrit.tt.security.staff.bean;

import com.tutrit.tt.security.staff.securityconfig.acl.AclAble;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department implements AclAble {
    @Id
    Long departmentId;
    String departmentName;

    public Long getId() {
        return this.departmentId;
    }

}
