package com.example.message.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("bidon")
    public String testing(){
        return "Biden mister";
    }

    @PostMapping("moin")
    public ResponseEntity<?> testeeer(@RequestParam (name = "Hello") String popa){
        System.out.println(popa);
        return new ResponseEntity<>(popa, HttpStatus.OK);
    }
}
