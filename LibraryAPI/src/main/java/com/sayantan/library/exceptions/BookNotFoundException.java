package com.sayantan.library.exceptions;

public class BookNotFoundException  extends RuntimeException
{
    public BookNotFoundException(Long id) {
        super("Id : "+ id);
    }
}
