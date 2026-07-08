package dhv.edu.demoSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DHVController {

    @GetMapping("/dhv")
    @ResponseBody
    public String listStudents() {
        return "<html><body><h1>Xin chào các bạn CT07</h1></body></html>";
    }
}