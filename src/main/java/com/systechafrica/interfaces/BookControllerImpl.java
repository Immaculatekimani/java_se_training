package com.systechafrica.interfaces;

import java.util.UUID;

public class BookControllerImpl implements BookController {

    @Override
    public Book createBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        return book;
    }

    @Override
    public boolean readBook(String isbn) {

        if (isbn.equals("A21")) {
            return true;
        }
        return false;
    }

    @Override
    public Book updateBook(String id, String isbn, String title, String authorName) {
        Book bookUpdate = new Book(isbn, title, authorName);
        bookUpdate.setId(id);
        return bookUpdate;
    }

    @Override
    public void deleteBook(String isbn) {
        if (isbn.equals("A30")) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("The book does not exist");
        }

    }

}
