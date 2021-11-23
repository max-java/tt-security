package com.tutrit.tt.security.quickstart.inmemory.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

/**
 * @ResponseBody is needed here to return content as is instead of call to ViewResolver and searching for view
 */
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecuredController {
    Navigation navigation;

    @GetMapping("/")
    @ResponseBody
    public String publicHomePage() {
        return navigation.get() + "this is public content";
    }

    @GetMapping("/authenticated")
    @ResponseBody
    public String authenticatedPage() {
        return navigation.get() + "this is content is visible only for authenticated users with any role";
    }

    @GetMapping("/user")
    @ResponseBody
    public String userPage() {
        return navigation.get() + "this content for users only";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String adminPage() {
        return navigation.get() + "this content for admins only";
    }

    @GetMapping("/mixed")
    @ResponseBody
    public String mixedAccessPage() {
        return navigation.get() + "this content available for admins and users";
    }

    @GetMapping("/switched")
    @ResponseBody
    public String switchedContentPage(@CurrentSecurityContext(expression = "authentication.authorities") List<SimpleGrantedAuthority> auth) {
        String content;
        if (auth.stream().anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN"))) {
            content = "<h1> admin content </h1>";
        } else if (auth.stream().anyMatch(a-> a.getAuthority().equals("ROLE_USER"))) {
            content = "<h1> user content </h1>";
        } else {
            content = "<h1> guesting </h1>";
        }
        return navigation.get() + "this is content is different for different users" + content;
        /*
         another way to get user details if from security Context with util class
         SecurityContext context = SecurityContextHolder.getContext();
         Authentication authentication = context.getAuthentication();
        */
    }
}
