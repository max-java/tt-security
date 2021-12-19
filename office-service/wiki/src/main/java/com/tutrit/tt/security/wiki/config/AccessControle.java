package com.tutrit.tt.security.wiki.config;

import com.tutrit.tt.security.custom.Principal;
import com.tutrit.tt.security.custom.SecurityContext;
import com.tutrit.tt.security.wiki.bean.AccessList;
import com.tutrit.tt.security.wiki.bean.Article;
import com.tutrit.tt.security.wiki.repository.AccessListRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccessControle {

    AccessListRepository accessListRepository;

    @Around("@annotation(com.tutrit.tt.security.wiki.config.AdminOnly)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (Arrays.stream(SecurityContext.currentUser().getRoles()).anyMatch(r -> "ADMIN".equals(r))) {
            return joinPoint.proceed();
        }
        LoggerFactory.getLogger(AccessControle.class).error("Access denied!");
        throw new RuntimeException("Access denied!");
    }

//    @AfterReturning(pointcut="execution(* com.tutrit.tt.security.wiki.ArticleService.findById(..))", returning = "resources")
//    public void onlyOwnerCanHaveAccess(Article resources) throws Throwable {
//        Principal principal = SecurityContext.currentUser();
//
//        LoggerFactory.getLogger(AccessControle.class).error("AfterReturning");
//    }

    @AfterReturning(value = "@annotation(com.tutrit.tt.security.wiki.config.OwnerOnly)", returning = "resources")
    public void onlyOwnerCanHaveAccess(Article resources) throws Throwable {
        LoggerFactory.getLogger(AccessControle.class).error("onlyOwnerCanHaveAccess to article!");
    }

    @AfterReturning(value = "@annotation(com.tutrit.tt.security.wiki.config.OwnerOnly)", returning = "resources")
    public void onlyOwnerCanHaveAccess(Optional resources) throws Throwable {
        LoggerFactory.getLogger(AccessControle.class).error("onlyOwnerCanHaveAccess to Optional!");
        throw new RuntimeException("Access denied!");
    }

    @AfterReturning(pointcut="execution(* com.tutrit.tt.security.wiki.ArticleService.findById())", returning = "resources")
    public void onlyOwnerCanHaveAccess2(Article resources) throws Throwable {
        LoggerFactory.getLogger(AccessControle.class).error("AfterReturning");

        Principal principal = SecurityContext.currentUser();
        AccessList al = accessListRepository.findAccessListByArticleId(resources.getArticleId());
        if (!al.getPrincipalId().equals(principal.getId())) {
            throw new RuntimeException("Access denied! Only User can have access!");
        }
    }
}
