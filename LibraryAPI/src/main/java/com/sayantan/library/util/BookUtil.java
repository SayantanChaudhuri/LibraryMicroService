package com.sayantan.library.util;

import com.sayantan.library.model.Book;
import com.sayantan.library.model.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookUtil {

    @Autowired
    private ModelMapper modelMapper;

    public BookEntity convertToEntity(Book book) {
        return modelMapper.map(book, BookEntity.class);
    }

    public Book convertToDto(BookEntity book) {
        log.info(book.toString());
        log.info(modelMapper.toString());
        return modelMapper.map(book, Book.class);
    }

    public List<Book> convertToDtoList(List<BookEntity> bookEntities) {

        return bookEntities
                .stream()
                .map(book -> convertToDto(book))
                .collect(Collectors.toList());
    }
}
