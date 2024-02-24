package org.group4.comp231.inventorymanagementservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.group4.comp231.inventorymanagementservice.validator.CodeValueValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CodeValueValidator.class)
public @interface ValidateCodeID {

    public String message() default "Invalid Code Value Id";
    public String codeTypeName();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
