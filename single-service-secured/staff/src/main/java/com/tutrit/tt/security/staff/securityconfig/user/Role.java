package com.tutrit.tt.security.staff.securityconfig.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @Enumerated(EnumType.STRING)
    RoleType role;
}
