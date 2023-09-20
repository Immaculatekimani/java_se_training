package com.systechafrica.interfaces;

public class Library {
    public static void main(String[] args) {
        BookController book = new BookControllerImpl();

        Book bk = new Book("A21", "Atomic Habits", "James Clear");
        Book bk2 = new Book("A22", "Ikigai", "James Clear");

        Book book1 = book.createBook(bk); // created the book
        System.out.println("The created book is: " + book1);
        Book book2 = book.createBook(bk2); // created the book
        System.out.println("The created book is: " + book2);

        Book foundBook = book.findBook("A21");
        System.out.println("The book you are looking for is: " + foundBook); // find book

        Book changeBook = book.updateBook(bk.getIsbn(), "The Game of Life");
        System.out.println("The new book update is " + changeBook); // update book

        book.deleteBook("A22"); // delete book
    }

}
