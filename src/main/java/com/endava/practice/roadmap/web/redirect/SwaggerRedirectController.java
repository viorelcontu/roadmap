package com.endava.practice.roadmap.web.redirect;

import com.endava.practice.roadmap.config.SwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ConditionalOnBean(SwaggerConfig.class)
public class SwaggerRedirectController {

        @GetMapping("/api/swagger")
        public String swaggerUi() {
            return "redirect:/swagger-ui.html?urls.primaryName=open-api";
        }
}
