package com.tutrit.tt.security.staff.config;

import com.tutrit.tt.security.custom.AuthProvider;
import com.tutrit.tt.security.custom.AuthRoboProvider;
import com.tutrit.tt.security.custom.AuthorizedEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    AuthProvider authProvider() {
        return new AuthProvider(){

            @Override
            public Long getUserId() {
                return 2L;
            }

            @Override
            public String getUserPass() {
                return "2";
            }

            @Override
            public String[] getUserRoles() {
                String[] roles = {"USER"};
                return roles;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    @Bean
    AuthRoboProvider authRoboProvider() {
        return new AuthRoboProvider() {
            @Override
            public String getApplicationId() {
                return "checkpointApp";
            }

            @Override
            public String getApplicationPass() {
                return "123456789";
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    @Bean
    public AuthorizedEndpoints personCard() {
        return new AuthorizedEndpoints() {
            @Override
            public String getUri() {
                return "/person/";
            }

            @Override
            public String getRole() {
                return "ADMIN";
            }
        };
    }

    @Bean
    public AuthorizedEndpoints personList() {
        return new AuthorizedEndpoints() {
            @Override
            public String getUri() {
                return "/person";
            }

            @Override
            public String getRole() {
                return "USER";
            }
        };
    }
}
