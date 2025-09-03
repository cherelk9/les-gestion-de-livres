package adapters;

import domain.models.Books;
import domain.models.BooksStatus;
import domain.ports.outbound.BooksRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BooksRepositoryAdapter implements BooksRepository {

    File file = new File("books.txt");


    @Override
    public Books saveBook(Books books) throws IOException {

        if (file.exists()) {
            try (
                    var ob = new ObjectInputStream(
                            new BufferedInputStream(
                                    new FileInputStream(file)
                            )
                    )){

                Object obj =  ob.readObject();

                if (obj instanceof Books newObj) {
                    return newObj;
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return new Books();
    }

/**
 * Methode findByIsbn
 * 1. entre dans le fichier
 * 2. on parcourt les elements du fichier
 * 3. si on trouve un, on retrouve un element qui correspond avec la variable que l'on recherche
 *      alors, on retourne cette valeur
 * 4. Sinon, on retourne une exception BookNotFoundException().
 * <p>
 *
 *
 * Algorithm findById(isbn : String) :Optional<Books>
 *     Begin
 *      if file exist
 *          Books[] books = file.readValue()
 *          for valeur in books
 *              if valeur.isbn ==isbn
 *                  return value
 *              else return null
 * */


    private Books findByIsbnOld(String isbn) throws IOException {
        if (file.exists())
            try (var ob = new ObjectInputStream(
                    new BufferedInputStream( new FileInputStream(file))
            )){
                Object oldBook = ob.readObject();
                if (oldBook instanceof Books newBook ){
                    if (newBook.getIsbn().equals(isbn))
                        return newBook;
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        return null;
    }


    private Books findByStatusOld(BooksStatus status) throws IOException{
        if (file.exists())
            try (var ob = new ObjectInputStream(
                    new BufferedInputStream( new FileInputStream(file))
            )){
                Object object = ob.readObject();
                if (object instanceof Books newBook)
                    if (newBook.getStatus().equals(status))
                        return newBook;

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return null;
    }



    /**
     * Methode findByIsbn
     * commence par creer un object Books qui contient le livre qu'on a retrouvé avec la methode findByIsbnOld
     * en suite, on peut creer une liste de livres qui contient l'ensemble des livres qu'on a retrouvés
     * en fin, on renvoie le premier livre s'il existe
     * */

    @Override
    public Optional<Books> findByIsbn(String isbn) throws IOException {
        Books book = findByIsbnOld(isbn);
        List<Books> books = new ArrayList<>();
        books.add(book);
        return books.stream().findFirst();
    }

    @Override
    public Optional<Books> findById(String id) throws IOException {
        Books book = findByIsbnOld(id);
        List<Books> books = new ArrayList<>();
        books.add(book);
        return books.stream().findFirst();
    }

    @Override
    public List<Books> findByStatus(BooksStatus status) throws IOException {
        Books book = findByStatusOld(status);
        List<Books> books = new ArrayList<>();
        books.add(book);
        return books;
    }


/**
 * Methode findAllBooks
 * On vérifie si le fichier existe
 * si oui alors recupere les elements qui se trouve dans ce fichier qu'on va stocker dans un fichier,
 * on vérifie ce nouvel objet est une liste
 *      si c'est le cas :
 *          on stocke les elements se trouve dans notre object qu'on a cree avant dans une nouvelle liste,
 *          on retourne la nouvelle liste.
 * */
    @Override
    public List<Books> findAllBooks() throws IOException {
        if (file.exists())
            try (var ob = new ObjectInputStream(
                    new BufferedInputStream( new FileInputStream(file))
            )){
                Object obj = ob.readObject();

                if (obj instanceof List<?>)
                {
                    @SuppressWarnings("unchecked")
                    List<Books> books = (List<Books>) obj;
                    return books;
                }

                return new ArrayList<>();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return new ArrayList<>();
    }


    /**
     * Methode existByIsbn
     * verifie si le fichier existe
     * si non retourn false
     *...
     * */
    @Override
    public boolean existsByIsbn(String isbn) throws IOException {
        if (!file.exists())
            return false;
        Optional<Books> optionalBooks = findByIsbn(isbn);
        return optionalBooks.isPresent();
    }
}
