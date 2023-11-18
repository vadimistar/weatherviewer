package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.domain.web.LoginModel;
import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.services.SessionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

import static com.vadimistar.weatherviewer.utils.Utils.addSessionCookie;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class LoginWebController {
    public static final String LOGIN_VIEW = "/login";
    public static final String DO_LOGIN = "/login";

    SessionService sessionService;

    @GetMapping(LOGIN_VIEW)
    public String loginView(Model model) {
        model.addAttribute("loginModel", new LoginModel("", ""));

        return "login";
    }

    @PostMapping(DO_LOGIN)
    public String doLogin(Model model,
                          HttpServletResponse response,
                          @ModelAttribute LoginModel loginModel) {
        try {
            SessionDto session = sessionService.createSession(loginModel.getName(), loginModel.getPassword());
            addSessionCookie(response, session);
            return "redirect:/";

        } catch (BadRequestException exception) {
            model.addAttribute("error", exception.getMessage());

        } catch (RuntimeException exception) {
            model.addAttribute("error", "something went wrong. try again later.");
        }

        model.addAttribute("loginModel", loginModel);

        return "login";
    }
}
