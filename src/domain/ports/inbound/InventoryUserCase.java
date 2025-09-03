package domain.ports.inbound;

import domain.exceptions.BookNotFoundException;
import domain.models.Books;
import domain.models.BooksStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InventoryUserCase {
    // commande
    Books addNewBook(AddBooksCommande commande) throws  FileNotFoundException;
    void updateBooksStatus(String isbn, BooksStatus status, String reason) throws  IOException;
    void handleBooksStatus(String isbn, String borrowerId) throws  IOException;
    void handleBooksReturned(String isbn) throws  IOException;

    //Queries
    Optional<Books> findByIsbn(String isbn) throws  IOException;
    List<Books> findAvailableBooks() throws  IOException;
    List<Books> searchBooks() throws  IOException;
    boolean isBookAvailable(String isbn) throws IOException;


    record AddBooksCommande(
            String isbn,
            String title,
            String author,
            String publisher,
            Integer publicationYear,
            String location
    ){}
}
