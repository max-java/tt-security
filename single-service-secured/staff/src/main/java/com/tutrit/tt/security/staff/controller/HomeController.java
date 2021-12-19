package com.tutrit.tt.security.staff.controller;


import com.tutrit.tt.security.staff.securityconfig.UserDetailsServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView openHomePage() {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Home");
        mov.setViewName("home");
        return mov;
    }
}
