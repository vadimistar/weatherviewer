package com.vadimistar.weatherviewer.controllers.web;

import com.vadimistar.weatherviewer.domain.web.RegisterModel;
import com.vadimistar.weatherviewer.dto.api.SessionDto;
import com.vadimistar.weatherviewer.exceptions.BadRequestException;
import com.vadimistar.weatherviewer.services.SessionService;
import com.vadimistar.weatherviewer.services.UserService;
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
public class RegisterWebController {
    public static final String REGISTER_VIEW = "/register";
    public static final String DO_REGISTER = "/register";

    SessionService sessionService;
    UserService userService;

    @GetMapping(REGISTER_VIEW)
    public String registerView(Model model) {
        model.addAttribute("registerModel", new RegisterModel("", "", ""));

        return "register";
    }

    @PostMapping(DO_REGISTER)
    public String doRegister(Model model,
                             HttpServletResponse response,
                             @ModelAttribute RegisterModel registerModel) {
        try {
            if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) {
                throw new BadRequestException("password and confirm password are different");
            }

            userService.createUser(registerModel.getName(), registerModel.getPassword());

            SessionDto session = sessionService.createSession(registerModel.getName(), registerModel.getPassword());
            addSessionCookie(response, session);
            return "redirect:/";

        } catch (BadRequestException exception) {
            model.addAttribute("error", exception.getMessage());

        } catch (RuntimeException exception) {
            model.addAttribute("error", "something went wrong. try again later.");
        }

        model.addAttribute("registerModel", registerModel);

        return "register";
    }
}
