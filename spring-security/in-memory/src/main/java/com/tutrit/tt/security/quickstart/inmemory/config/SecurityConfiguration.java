package com.tutrit.tt.security.quickstart.inmemory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Common way of a WebSecurityConfigurerAdapter implementation
 *
 * FYI:
 * {noop}   NoOpPasswordEncoder
 * {bcrypt} BCryptPasswordEncoder
 * {pbkdf2} Pbkdf2PasswordEncoder
 * {scrypt} SCryptPasswordEncoder
 * {sha256} StandardPasswordEncoder
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .and()
                .withUser("guest")
                .password("{noop}guest")
                .roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/mixed").hasAnyRole("ADMIN","USER")
                .antMatchers("/", "/login", "/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .httpBasic() // login form is not required
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }
}
