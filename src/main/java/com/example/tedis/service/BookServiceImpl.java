package com.example.tedis.service;

import com.example.tedis.model.Book;
import com.example.tedis.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getPagedList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int stop = page * pageSize - 1;

        return bookRepository.findByRange(start, stop);
    }

    @Override
    public Book getById(String isbn) {
        return bookRepository.find(isbn);
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> addAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public boolean remove(String isbn) {
        return bookRepository.remove(isbn);
    }
}
