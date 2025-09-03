package domain.models;

import domain.exceptions.BooksException;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *Books (
 *     String id,
 *     String isbn,
 *     String title,
 *     String author,
 *     String publisher,
 *     Integer publicationYear,
 *     String location,
 *     BooksStatus status,   # BooksStatus est une enumeration
 *     LocalDateTime lastStatusChange,
 *     String lastBorrowerId
 * );
 * */

public class Books implements Serializable {

    Books books;

    private String id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String location;
    private BooksStatus status;
    private LocalDateTime lastStatusChange;
    private String lastBorrowerId;

    public Books(){}

    public Books(
            String isbn,
            String title,
            String author,
            String publisher,
            int publicationYear,
            String location) {

        this.isbn =isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.location = location;
    }

    public Books(
            String id,
            String isbn,
            String title,
            String author,
            String publisher,
            int publicationYear,
            String location,
            BooksStatus status,
            LocalDateTime lastStatusChange,
            String lastBorrowerId) {

        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.location = location;
        this.status = status;
        this.lastStatusChange = lastStatusChange;
        this.lastBorrowerId = lastBorrowerId;
    }


    public String getId() {return id;}
    public LocalDateTime getLastStatusChange() {return lastStatusChange;}
    public String getLastBorrowerId() {return lastBorrowerId;}
    public BooksStatus getStatus() {return status;}
    public String getLocation() {return location;}
    public int getPublicationYear() {return publicationYear;}
    public String getPublisher() {return publisher;}
    public String getAuthor() {return author;}
    public String getTitle() {return title;}
    public String getIsbn() {return isbn;}


    public void markAsBorrow(String borrowerId) throws BooksException{
        if (this.status != BooksStatus.AVAILABLE) {
            throw new BooksException(" book" +this.isbn+ "cannot borrow  . current status " +this.status  );
        }
        this.status = BooksStatus.BORROWER;
        this.lastBorrowerId = borrowerId;
        this.lastStatusChange = LocalDateTime.now();
    }


    public void markAsReserved() throws BooksException{
        if (this.status != BooksStatus.AVAILABLE) {
            throw new BooksException(" book" +this.isbn+ "cannot reserved  . current status " +this.status );
        }

        this.status = BooksStatus.RESERVED;
        this.lastStatusChange = LocalDateTime.now();
    }

    public void markAsReturned() {
        if (this.status != BooksStatus.BORROWER)
            throw new BooksException(" book" +this.isbn+ "cannot available  . current status " +this.status );

        this.status = BooksStatus.AVAILABLE;
        this.lastStatusChange = LocalDateTime.now();
    }

    public boolean isAvailable() {
        return  this.status == BooksStatus.AVAILABLE;
    }
}
