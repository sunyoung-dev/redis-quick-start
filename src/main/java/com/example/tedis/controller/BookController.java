package com.example.tedis.controller;

import com.example.tedis.model.Book;
import com.example.tedis.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list")
    public List<Book> getList(int page, int pageSize) {
        return bookRepository.getList(page, pageSize);
    }

    @GetMapping("/{isbn}")
    public Book get(@PathVariable String isbn) {
        return bookRepository.get(isbn);
    }

    @PostMapping
    public Book add(@RequestBody Book book) {
        return bookRepository.add(book);
    }

    @PostMapping("/list")
    public List<Book> addList(@RequestBody List<Book> books) {
        return bookRepository.addAll(books);
    }

    @DeleteMapping("/{isbn}")
    public Book delete(@PathVariable String isbn) {
        return bookRepository.remove(isbn);
    }
}
