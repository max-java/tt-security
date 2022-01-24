package com.tutrit.tt.security.staff.securityconfig.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    String username;
    String password;
    // TODO: 1/16/22 add filter by custom user field in PostAuthorize
    String fullName;
    @OneToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();
}
