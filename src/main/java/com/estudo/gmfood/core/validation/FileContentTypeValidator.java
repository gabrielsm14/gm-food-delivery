package com.estudo.gmfood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentType;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentType = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
      return value == null || this.allowedContentType.contains(value.getContentType());
    }
}