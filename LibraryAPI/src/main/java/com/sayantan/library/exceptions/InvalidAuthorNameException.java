package com.sayantan.library.exceptions;

public class InvalidAuthorNameException extends RuntimeException
{
    public InvalidAuthorNameException(String exception) {
        super("Authors name(s) : " + exception);
    }
}