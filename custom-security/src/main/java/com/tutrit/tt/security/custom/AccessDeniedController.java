package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccessDeniedController {

    @GetMapping("/403")
    public ModelAndView openLoginForm() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("403");
        return mov;
    }
}
