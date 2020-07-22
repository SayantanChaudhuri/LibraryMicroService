package com.sayantan.library.controllers;

import com.sayantan.library.exceptions.InvalidAuthorNameException;
import com.sayantan.library.model.Book;
import com.sayantan.library.model.Books;
import com.sayantan.library.services.BooksServiceImpl;
import com.sayantan.library.validators.BookCustomValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BooksController {

    private final BooksServiceImpl booksService;
    private final BookCustomValidator bookCustomValidator;

    @Autowired
    public BooksController(BooksServiceImpl booksService,
                           BookCustomValidator bookCustomValidator) {

        this.booksService = booksService;
        this.bookCustomValidator = bookCustomValidator;
    }

    @GetMapping("/books")
    public ResponseEntity<Books> getAllBooks(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(defaultValue = "bookName") String sortBy) {

        Books books = booksService.getAllBooks(pageNo, pageSize, sortBy);
        log.info("Books result : {}", books);
        return new ResponseEntity<Books>(books, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Books> saveBook(@RequestBody @Valid Book book, BindingResult errors) {
        log.info("Book save entry : {}", book);

        bookCustomValidator.validate(book, errors);

        if (errors.hasErrors())
            throw new InvalidAuthorNameException(errors.getAllErrors().get(0).getDefaultMessage());

        Books books = booksService.saveBook(book);
        log.info("Books save result : {}", books);
        return new ResponseEntity<Books>(books, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = booksService.getBookById(id);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable("id") Long id, @RequestBody Book book,
                                            @RequestParam(defaultValue = "0") int pageNo,
                                            @RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(defaultValue = "bookName") String sortBy) {

        Books books = booksService.updateBook(id, book, pageNo, pageSize, sortBy);
        return new ResponseEntity<Books>(books, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Books> deleteBook(@PathVariable("id") Long id) {
        Books books = booksService.deleteById(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/books")
    public ResponseEntity<Books> deleteAllBooks() {
        booksService.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

