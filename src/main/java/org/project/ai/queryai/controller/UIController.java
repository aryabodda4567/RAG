package org.project.ai.queryai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UIController {

    public String index(){
        return "index";
    }
}
