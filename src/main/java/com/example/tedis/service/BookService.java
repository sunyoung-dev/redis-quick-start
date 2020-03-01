package com.example.tedis.service;

import com.example.tedis.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getPagedList(int page, int pageSize);

    Book getById(String isbn);

    Book add(Book book);

    List<Book> addAll(List<Book> books);

    boolean remove(String isbn);
}
