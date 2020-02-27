package com.example.tedis.repository;

import com.example.tedis.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getList(int page, int pageSize);

    Book get(String isbn);

    Book add(Book book);

    List<Book> addAll(List<Book> books);

    Book remove(String isbn);
}