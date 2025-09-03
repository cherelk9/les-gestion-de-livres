package application;

import domain.exceptions.BookNotFoundException;
import domain.exceptions.BooksException;
import domain.models.Books;
import domain.models.BooksStatus;
import domain.ports.inbound.InventoryUserCase;
import domain.ports.outbound.BooksRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class InventoryService implements InventoryUserCase {
    File file = new File("books.txt");

    private final BooksRepository booksRepository;

    public InventoryService(BooksRepository  booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Books addNewBook(AddBooksCommande commande) throws FileNotFoundException {

        if (file.exists() ){
            try (var ob = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)
                    )
            )) {

                if (!booksRepository.existsByIsbn(commande.isbn())) {
                    Books newBook = new Books(
                            commande.isbn(),
                            commande.title(),
                            commande.author(),
                            commande.publisher(),
                            commande.publicationYear(),
                            commande.location()
                    );
                    ob.writeObject(newBook);
                }
                throw new BooksException("book with ISBN" +commande.isbn()+ "already exist  !");


            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }
        return null;
    }

    @Override
    public void updateBooksStatus(String isbn, BooksStatus status, String reason)
            throws IOException {

        if (file.exists()) {
            try (
                    var ab = new ObjectOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(file)
                            )
                    )){
                Books newBook = booksRepository.findByIsbn(isbn)
                        .orElseThrow(BookNotFoundException::new);

                BooksStatus oldStatus = newBook.getStatus();

                switch (status) {
                    case BORROWER -> newBook.markAsBorrow(reason);
                    case AVAILABLE -> newBook.markAsReturned();
                    case MAINTENANCE -> newBook.markAsReserved();
                    default -> throw new BookNotFoundException("status change not supported" +oldStatus);
                }

                booksRepository.saveBook(newBook);

            }
        }

    }

    @Override
    public void handleBooksStatus(String isbn, String borrowerId) throws IOException {
        updateBooksStatus(isbn, BooksStatus.BORROWER ,borrowerId);
    }

    @Override
    public void handleBooksReturned(String isbn) throws IOException {
        updateBooksStatus(isbn, BooksStatus.AVAILABLE, "Retouner");

    }

    @Override
    public Optional<Books> findByIsbn(String isbn) throws IOException {
        return booksRepository.findByIsbn(isbn);
    }

    @Override
    public List<Books> findAvailableBooks() throws IOException {
        return booksRepository.findByStatus(BooksStatus.AVAILABLE);
    }

    @Override
    public List<Books> searchBooks() throws  IOException {
        return booksRepository.findAllBooks();
    }

    @Override
    public boolean isBookAvailable(String isbn) throws  IOException {
        return booksRepository.existsByIsbn(isbn);
    }
}
