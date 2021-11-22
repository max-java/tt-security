package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LoginController {

    AuthService authService;

    @GetMapping("/login")
    public ModelAndView openLoginForm() {
        ModelAndView mov = new ModelAndView();
        mov.addObject("principal", new Principal());
        mov.setViewName("login_form");
        return mov;
    }

    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute Principal principal,
                                   HttpServletRequest request) {
        if (authService.authorize(principal.getId(), principal.getPassword())) {
            principal.setFullName("Maksim Shelkovich");
            request.getSession().setAttribute("principal", principal);
        }
        return "redirect:/";
    }

    @GetMapping(value="/logout")
    public String invalidate(HttpServletRequest request,
                             Model model) {
        request.getSession().invalidate();
        model.asMap().clear();
        return "redirect:/";
    }
}
