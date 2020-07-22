package com.sayantan.library.services;

import com.sayantan.library.exceptions.BookNotFoundException;
import com.sayantan.library.model.Book;
import com.sayantan.library.model.BookEntity;
import com.sayantan.library.model.Books;
import com.sayantan.library.repository.BookRepository;
import com.sayantan.library.util.BookUtil;
import com.sayantan.library.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookUtil bookUtil;


    @Override
    public Books getAllBooks(Integer pageNo, Integer pageSize, String sortBy) {
        log.info("{}, {}, {}", pageNo, pageSize, sortBy);
        Books books = new Books();
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy).ignoreCase();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(order));
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

        if (bookEntities.hasContent()) {
            log.info(String.valueOf(bookEntities.getContent()));
            books.setBooks(bookUtil.convertToDtoList(bookEntities.getContent()));
        } else {
            books.setBooks(new ArrayList<>());
        }

        books.setTotalPageCount(bookEntities.getTotalPages());
        books.setTotalBooks(bookEntities.getTotalElements());

        return books;
    }

    @Override
    public Books saveBook(Book book) {
        log.info("Book : {}", book);
        bookRepository.save(bookUtil.convertToEntity(book));
        return this.getAllBooks(Constant.DEFAULT_PAGE_NO, Constant.DEFAULT_PAGE_SIZE, Constant.DEFAULT_SORT_BY);
    }

    @Override
    public Book getBookById(Long id) {
        Optional<BookEntity> bookData = bookRepository.findById(id);

        if (!bookData.isPresent())
            throw new BookNotFoundException(id);

        return bookUtil.convertToDto(bookData.get());
    }

    @Override
    public Books updateBook(Long id, Book book, int pageNo, int pageSize, String sortBy) {
        log.info("Book : {}", book);

        Optional<BookEntity> bookData = bookRepository.findById(id);

        if (!bookData.isPresent())
            throw new BookNotFoundException(id);

        bookRepository.save(bookUtil.convertToEntity(book));
        return this.getAllBooks(pageNo, pageSize, sortBy);
    }

    @Override
    public Books deleteById(Long id) {
        Optional<BookEntity> bookData = bookRepository.findById(id);

        if (!bookData.isPresent())
            throw new BookNotFoundException(id);

        bookRepository.deleteById(id);
        return this.getAllBooks(Constant.DEFAULT_PAGE_NO, Constant.DEFAULT_PAGE_SIZE, Constant.DEFAULT_SORT_BY);
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
