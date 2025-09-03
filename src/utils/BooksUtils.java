package utils;

import java.io.*;

public class  BooksUtils <P, R> {
    private final File file = new File("books.txt");

    private final P p;
    private final R r;

    public BooksUtils(P p, R r) {
        this.p = p;
        this.r = r;
    }

    public R getR() {return r;}
    public P  getP() {return p;}

}
