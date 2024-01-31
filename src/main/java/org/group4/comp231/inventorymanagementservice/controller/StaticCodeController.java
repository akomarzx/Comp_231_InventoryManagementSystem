package org.group4.comp231.inventorymanagementservice.controller;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.repository.CodeBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class StaticCodeController {

    private final CodeBookRepository codeBookRepository;

    public StaticCodeController(CodeBookRepository codeBookRepository) {
        this.codeBookRepository = codeBookRepository;
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<CodeBook>> getAllStaticValues(){
        List<CodeBook> valueList = this.codeBookRepository.findAll();
        return new ResponseEntity<>(valueList, HttpStatus.OK);
    }
}
