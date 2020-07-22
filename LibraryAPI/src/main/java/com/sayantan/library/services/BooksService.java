package com.sayantan.library.services;

import com.sayantan.library.model.Book;
import com.sayantan.library.model.BookEntity;
import com.sayantan.library.model.Books;

import java.util.List;

public interface BooksService {
    Books getAllBooks(Integer pageNo, Integer pageSize, String sortBy);
    Books saveBook(Book book);

    Book getBookById(Long id);

    Books updateBook(Long id, Book book, int pageNo, int pageSize, String sortBy);

    Books deleteById(Long id);

    void deleteAll();
}
