package com.systechafrica.interfaces;

public class Library {
    public static void main(String[] args) {
        BookController book = new BookControllerImpl();
        Book bk = new Book("A21", "Atomic Habits", "James Clear");
        Book newBook = book.createBook(bk); // created the book
        System.out.println("The created book is: " + newBook);
        boolean isAvailable = book.readBook(bk.getIsbn()); // find the book
        if (isAvailable == true) {
            System.out.println("The book with the number: " + bk.getIsbn() + " is available");
        } else {
            System.out.println("Book not available!");
        }
        Book changeBook = book.updateBook(bk.getId(), "A30", "6AM CLUB", "Robin Sharma");// udating a book
        System.out.println("The new book is " + changeBook);

        book.deleteBook(bk.getIsbn());
        book.deleteBook(changeBook.getIsbn()); // delete book
    }

}
