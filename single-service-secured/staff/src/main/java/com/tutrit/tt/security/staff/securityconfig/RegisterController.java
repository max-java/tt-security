package com.tutrit.tt.security.staff.securityconfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    private final UserDetailsServiceImpl userService;

    public RegisterController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        var mov = new ModelAndView();
        mov.setViewName("registrationForm");
        return mov;
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String retypePassword) {
        try {
            userService.registerUser(username, password, retypePassword);
            return "redirect:/login";
        } catch (Exception e) {
            return "redirect:/registration";
        }
    }
}
