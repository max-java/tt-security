package com.tutrit.tt.security.staff.securityconfig.annotation;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PostFilter("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @userDetailsServiceImpl.getFullUserName() == filterObject.fullName)")
public @interface AdminRwAllUserRwOnlyFilteredByFullName {

}
