package com.tutrit.tt.security.staff.securityconfig;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, String> {
}
