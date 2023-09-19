package com.systechafrica.interfaces;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private String authorName;

    public Book(String isbn, String title, String authorName) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", authorName=" + authorName + "]";
    }

}
