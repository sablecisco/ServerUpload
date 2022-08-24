package Focus_Zandi.version1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/developers")
    public String dev() {
        return "basic/index.html";
    }
}
