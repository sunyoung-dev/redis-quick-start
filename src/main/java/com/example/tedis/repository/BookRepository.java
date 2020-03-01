package com.example.tedis.repository;

import com.example.tedis.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findByRange(int start, int stop);

    Book find(String isbn);

    Book save(Book book);

    List<Book> saveAll(List<Book> books);

    boolean remove(String isbn);
}