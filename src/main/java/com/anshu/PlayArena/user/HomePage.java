package com.anshu.PlayArena.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {
    @GetMapping("/")
    public String homePage() {
        return "You are on my homepage !!";
    }
}
