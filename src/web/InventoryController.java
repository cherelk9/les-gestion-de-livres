package web;

import domain.models.Books;
import domain.ports.inbound.InventoryUserCase;

import java.io.*;
import java.util.Optional;

public class InventoryController {

    private final File file = new File("books.txt");

    private final InventoryUserCase inventoryUserCase;

    public InventoryController(InventoryUserCase inventoryUserCase) {
        this.inventoryUserCase = inventoryUserCase;
    }


    public void addBooks(AddBookRequest addBookRequest) throws IOException, InterruptedException {

        if (file.exists()) {
            try (var ob = new ObjectOutputStream(
                    new BufferedOutputStream( new FileOutputStream(file))
            )){
                Books books = inventoryUserCase.addNewBook(
                        new InventoryUserCase.AddBooksCommande(
                                addBookRequest.isbn,
                                addBookRequest.title,
                                addBookRequest.author,
                                addBookRequest.publisher,
                                addBookRequest.publicationYear,
                                addBookRequest.location
                        )
                );
                ob.writeObject(books);
            }
            Thread.sleep(10000);
            System.out.println("le ficheier a ete enregistre !" );

        }

    }

    public Optional<Books> getBook(String isbn) throws IOException {
        if (!file.exists())
            return Optional.empty();

        return inventoryUserCase
                .findByIsbn(isbn)
                .filter(p-> BooksResponse.from(p).available());
    }

    //Dto
    record AddBookRequest(
            String isbn,
            String title,
            String author,
            String publisher,
            Integer publicationYear,
            String location
    ) {}

    record BooksResponse(
            String id,
            String isbn,
            String title,
            String author,
            String publisher,
            Integer publicationYear,
            String location,
            String status,
            boolean available
    ){
        static BooksResponse from(Books book) {
            return new BooksResponse(
                    book.getId(),
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getPublicationYear(),
                    book.getLocation(),
                    book.getStatus().name(),
                    book.isAvailable()
            );
        }
    }

    record AvailabilityResponse(String isbn, boolean available) {}

}
