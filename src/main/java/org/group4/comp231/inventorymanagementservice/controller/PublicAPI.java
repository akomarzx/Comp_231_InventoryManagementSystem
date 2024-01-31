package org.group4.comp231.inventorymanagementservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: For testing purposes
@RestController
@RequestMapping("/public")
@Slf4j
public class PublicAPI {

    @GetMapping("/message")
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Hello World - Public", HttpStatus.OK);
    }
}
