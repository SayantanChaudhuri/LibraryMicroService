package com.sayantan.library.validators;

import com.sayantan.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
@Data
@NoArgsConstructor
public class BookCustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        log.info("book : {}", book);
        boolean isValid = false;

        if(book == null || book.getBookName() == null || book.getBookName().isEmpty())
            errors.rejectValue("bookName", "Invalid", new Object[]{"bookName"}, "Invalid book name");
    }
}
