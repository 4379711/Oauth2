package com.yalong.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyalong
 */
@RestController
public class IndexController {
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/user")
    public String user(){
        return "I'm index in source-server";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/admin")
    public String admin(){
        return "I'm admin in source-server";
    }
}
