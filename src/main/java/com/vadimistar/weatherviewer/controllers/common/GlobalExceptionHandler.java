package com.vadimistar.weatherviewer.controllers.common;

import com.vadimistar.weatherviewer.dto.api.ErrorDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class GlobalExceptionHandler implements ErrorController {
    public static final String ERROR = "/error";

    ErrorAttributes errorAttributes;

    @RequestMapping(ERROR)
    public ResponseEntity<ErrorDto> error(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        );

        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(ErrorDto
                        .builder()
                        .error((String) attributes.get("message"))
                        .build()
                );
    }
}
