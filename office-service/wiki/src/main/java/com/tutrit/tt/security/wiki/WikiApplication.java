package com.tutrit.tt.security.wiki;

import com.tutrit.tt.security.custom.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication(scanBasePackages = "com.tutrit")
public class WikiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(WikiApplication.class, args);
//        ctx.getBean(AuthFilter.class);
        ctx.getBeansOfType(AuthFilter.class);
    }
}
