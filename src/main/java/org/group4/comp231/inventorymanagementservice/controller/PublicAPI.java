package org.group4.comp231.inventorymanagementservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: For testing purposes
@RestController
@RequestMapping("/public")
public class PublicAPI {

    @GetMapping("/message")
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Hello World - Public", HttpStatus.OK);
    }

    @GetMapping("/message/messages")
    public ResponseEntity<String> getMessage2() {
        return new ResponseEntity<>("Hello World - Public 2", HttpStatus.OK);
    }

    @GetMapping("inventory")
    public ResponseEntity<String> getMessage3() {
        return new ResponseEntity<>("Hello World - Public 3 Inventory", HttpStatus.OK);
    }
}
