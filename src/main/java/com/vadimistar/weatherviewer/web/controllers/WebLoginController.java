package com.vadimistar.weatherviewer.web.controllers;

import com.vadimistar.weatherviewer.api.dto.SessionDto;
import com.vadimistar.weatherviewer.api.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.api.services.SessionService;
import com.vadimistar.weatherviewer.web.models.SearchModel;
import com.vadimistar.weatherviewer.web.models.UserInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

import static com.vadimistar.weatherviewer.api.utils.Utils.addSessionCookie;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class WebLoginController {
    public static final String LOGIN_VIEW = "/login";
    public static final String DO_LOGIN = "/login";

    SessionService sessionService;

    @GetMapping(LOGIN_VIEW)
    public String loginView(Model model) {
        model.addAttribute("userInfo", new UserInfo("", ""));

        return "login";
    }

    @PostMapping(DO_LOGIN)
    public String loginView(Model model,
                            HttpServletResponse response,
                            @ModelAttribute UserInfo userInfo) {
        try {
            SessionDto session = sessionService.createSession(userInfo.getName(), userInfo.getPassword());
            addSessionCookie(response, session);
            return "redirect:/";

        } catch (BadRequestException exception) {
            model.addAttribute("error", exception.getMessage());

        } catch (RuntimeException exception) {
            model.addAttribute("error", "something went wrong. try again later.");
        }

        model.addAttribute("userInfo", userInfo);

        return "login";
    }
}
