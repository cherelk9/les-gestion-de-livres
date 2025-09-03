package domain.exceptions;

import java.io.IOException;

public class BookNotFoundException extends IOException {

    public BookNotFoundException() {}

    public BookNotFoundException(String message) {
        super(message);
    }
}
