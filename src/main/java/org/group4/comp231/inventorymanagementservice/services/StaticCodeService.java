package org.group4.comp231.inventorymanagementservice.services;

import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.repository.CodeBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StaticCodeService {

    private final CodeBookRepository codeBookRepository;
    private List<CodeBook> codeBook;

    public final Long CODEBOOK_COUNTRY_ID = 10000L;
    public  final Long CODEBOOK_PROVINCE_ID = 20000L;
    public final Long CODEBOOK_GROUP_ID = 70000L;

    public StaticCodeService(CodeBookRepository codeBookRepository) {
        this.codeBookRepository = codeBookRepository;
        this.codeBook = this.codeBookRepository.findAll();
    }

    public List<CodeBook> getAllStaticCode() {
        if (codeBook == null) {
            this.codeBook = this.codeBookRepository.findAll();
        }

        return this.codeBook;
    }

    public Optional<CodeBook> getGCodeValueListUsingCodeBookID(Long codebookID) {
        if(codeBook != null) {
            return this.codeBook.stream().filter(codeBook1 -> Objects.equals(codeBook1.getId(), codebookID)).findFirst();
        }
        return Optional.empty();
    }
}
