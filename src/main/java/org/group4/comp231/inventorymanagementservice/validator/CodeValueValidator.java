package org.group4.comp231.inventorymanagementservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.annotation.ValidateCodeID;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.service.StaticCodeService;

import java.util.Objects;
import java.util.Optional;

public class CodeValueValidator implements ConstraintValidator<ValidateCodeID, Long> {

    private static final Log log = LogFactory.getLog(CodeValueValidator.class);
    private final StaticCodeService staticCodeService;
    private Long codeBookID;
    public CodeValueValidator(StaticCodeService staticCodeService) {
        this.staticCodeService = staticCodeService;
    }

    @Override
    public void initialize(ValidateCodeID constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.codeBookID = this.getCodeBookID(constraintAnnotation.codeTypeName());
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        Optional<CodeBook> codeValueList = this.staticCodeService.getGCodeValueListUsingCodeBookID(codeBookID);
        if (id == null) {
            return true;
        }
        return codeValueList.map(codeBook -> codeBook.getCodeValues().stream().anyMatch(codeValue -> Objects.equals(codeValue.getId(), id))).orElse(false);
    }

    private Long getCodeBookID(String parameter) {
        if (parameter.equalsIgnoreCase("PROVINCE")) {
            return this.staticCodeService.CODEBOOK_PROVINCE_ID;
        } else if (parameter.equalsIgnoreCase("COUNTRY")) {
            return  this.staticCodeService.CODEBOOK_COUNTRY_ID;
        } else if (parameter.equalsIgnoreCase("ACCOUNTTYPE")) {
            return this.staticCodeService.ACCOUNT_TYPE_ID;
        } else {
            return null;
        }
    }
}
