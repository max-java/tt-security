package com.tutrit.tt.security.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.tutrit.tt.security.custom.MyProperties.REDIRECTED_URL_ATTRIBUTE;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LoginController {

    AuthProvider authProvider;
    PrincipalMapper principalMapper;

    @GetMapping("/login")
    public ModelAndView openLoginForm(HttpServletRequest request) {
        ModelAndView mov = new ModelAndView();
        mov.addObject("principal", new Principal());
        mov.addObject(REDIRECTED_URL_ATTRIBUTE, request.getParameter(REDIRECTED_URL_ATTRIBUTE));
        mov.setViewName("login_form");
        return mov;
    }

    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute Principal principal,
                                   HttpServletRequest request) {
        if (authProvider.authorize(principal.getId(), principal.getPassword())) {
            principal.setFullName("Maksim Shelkovich");
            principal.setRoles(authProvider.getUserRoles());
            principal.setIsAuthenticated(true);
            request.getSession().setAttribute("principal", principal);
        }
        String redirectionValue = request.getParameter(REDIRECTED_URL_ATTRIBUTE)+"?principal="+principal.serializeToString();
        return "redirect:"+ redirectionValue;
    }

    @GetMapping(value="/logout")
    public String invalidate(HttpServletRequest request,
                             Model model) {
        request.getSession().invalidate();
        model.asMap().clear();
        return "redirect:/";
    }
}
