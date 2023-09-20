package com.systechafrica.interfaces;

public interface BookController {
    Book createBook(Book book);

    Book findBook(String isbn);

    Book updateBook(String isbn, String title);

    void deleteBook(String isbn);

}
