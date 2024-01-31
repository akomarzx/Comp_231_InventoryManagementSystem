package org.group4.comp231.inventorymanagementservice.services;

import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.repository.CodeBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticCodeService {

    private final CodeBookRepository codeBookRepository;
    private List<CodeBook> codeBook;

    public StaticCodeService(CodeBookRepository codeBookRepository) {
        this.codeBookRepository = codeBookRepository;
        this.codeBook = this.codeBookRepository.findAll();
    }

    public List<CodeBook> getAllStaticCode() {
        if(codeBook == null) {
            this.codeBook = this.codeBookRepository.findAll();
        }

        return this.codeBook;
    }
}
