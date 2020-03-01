package com.alivn.tools.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: lijinpeng
 * Created by Shanghai on 2020/2/29
 */
@Controller
public class IndexController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
