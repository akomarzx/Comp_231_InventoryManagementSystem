package org.group4.comp231.inventorymanagementservice.service;

import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.repository.CodeBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service for Static Code for the API
 */
@Service
public class StaticCodeService extends BaseService {

    private final CodeBookRepository codeBookRepository;

    private List<CodeBook> codeBook;

    public final Long CODEBOOK_COUNTRY_ID = 10000L;
    public final Long CODEBOOK_PROVINCE_ID = 20000L;
    public final Long CODEBOOK_ACCOUNT_TYPE_ID = 60000L;
    public final Long CODEBOOK_GROUP_ID = 70000L;
    public final Long CODEBOOK_ORDER_TYPE_ID = 50000L;
    public final Long CODEBOOK_PURCHASE_ORDER_STATUS_ID = 90000L;
    public final Long CODEBOOK_SALES_ORDER_STATUS_ID = 30000L;

    public final Long VENDOR_ACCOUNT_CODE_VALUE_ID = 600010L;
    public final Long SELLER_ACCOUNT_CODE_VALUE_ID = 600020L;

    public final Long PURCHASE_ORDER_CODE_VALUE_ID = 500010L;
    public final Long SALES_ORDER_CODE_VALUE_ID = 500020L;

    public final Long PENDING_SALES_ORDER_STATUS_CODE_VALUE_ID = 300010L;
    public final Long PENDING_PURCHASE_ORDER_STATUS_CODE_VALUE_ID = 900010L;

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

    public Optional<CodeBook> getCodeValueListUsingCodeBookID(Long codebookID) {
        if(codeBook != null) {
            return this.codeBook.stream().filter(codeBook1 -> Objects.equals(codeBook1.getId(), codebookID)).findFirst();
        }
        return Optional.empty();
    }

}
