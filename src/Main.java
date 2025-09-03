import application.InventoryService;
import domain.models.Books;
import domain.models.BooksStatus;
import domain.ports.outbound.BooksRepository;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    static BooksRepository booksRepository;
    /**
     * Description
     * gestion d'une bibliothèque avec une architecture hexagonale
     * <p>
     *      String id,
     *  *     String isbn,
     *  *     String title,
     *  *     String author,
     *  *     String publisher,
     *  *     Integer publicationYear,
     *  *     String location,
     *  *     BooksStatus status,   # BooksStatus est une enumeration
     *  *     LocalDateTime lastStatusChange,
     *  *     String lastBorrowerId
     *
     * AUTHOR : cherelk9
     * </p>
     * */
    public static void main(String[] args) throws IOException {
        var localeDateTime = LocalDateTime.now();

        Books book = new Books("2",
                "amour", "le feu", "lionel",
                "cem",2023, "oui",
                BooksStatus.AVAILABLE, localeDateTime, "12" );
        try {
            Books newBooks = new InventoryService().addNewBook(book);
        }


    }
}