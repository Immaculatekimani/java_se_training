package com.systechafrica.interfaces;

import java.util.UUID;

public class BookControllerImpl implements BookController {
    Book[] library = new Book[10];

    @Override
    public Book createBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        for (int i = 0; i < library.length; i++) {
            if (library[i] == null) {
                library[i] = book;
            }
        }
        return book;
    }

    @Override
    public Book findBook(String isbn) {
        for (Book i : library) {
            if (i.getIsbn().equals(isbn)) {
                return i;
            }
        }
        return null;

    }

    @Override
    public Book updateBook(String isbn, String title) {
        Book book = findBook(isbn);
        book.setTitle(title);
        return book;
    }

    @Override
    public void deleteBook(String isbn) {
        for (int i = 0; i < library.length; i++) {
            if ((library[i] != null) && (library[i].getIsbn().equals(isbn))) {
                library[i] = null;
                System.out.println("Book successfully deleted");
                return;
            }
        }
    }

}
