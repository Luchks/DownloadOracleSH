package com.ejemplo.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/")
    public String saludar() {
        return "¡Hola mundo desde Spring hecho a mano! 🎉";
    }
}

