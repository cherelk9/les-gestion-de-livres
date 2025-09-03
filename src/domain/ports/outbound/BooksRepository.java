package domain.ports.outbound;

import domain.models.Books;
import domain.models.BooksStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BooksRepository {
    Books saveBook(Books books) throws IOException;
    Optional<Books> findByIsbn(String isbn) throws IOException;
    Optional<Books> findById(String id) throws IOException;
    List<Books> findByStatus(BooksStatus status) throws IOException;
    List<Books> findAllBooks() throws IOException;
    boolean existsByIsbn(String isbn) throws IOException;
}
