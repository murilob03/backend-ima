package com.imobarea.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("imoveis")
public class ApiController {
    
    @GetMapping
    public void getAll() 
    {

    }
}
