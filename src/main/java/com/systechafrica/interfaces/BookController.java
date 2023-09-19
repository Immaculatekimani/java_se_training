package com.systechafrica.interfaces;

public interface BookController {
    Book createBook(Book book);

    boolean readBook(String isbn);

    Book updateBook(String id, String isbn, String title, String authorName);

    void deleteBook(String isbn);

}
