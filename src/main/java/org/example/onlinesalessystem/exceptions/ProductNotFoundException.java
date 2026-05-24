package org.example.onlinesalessystem.exceptions;

import java.time.format.SignStyle;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
