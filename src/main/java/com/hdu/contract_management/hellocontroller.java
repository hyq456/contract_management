package com.hdu.contract_management;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellocontroller {
    @RequestMapping("/hello")
    public String hello(){
        return "HELLO SPRING BOOT";
    }
}
