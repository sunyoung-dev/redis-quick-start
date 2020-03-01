package com.example.tedis.controller;

import com.example.tedis.model.Book;
import com.example.tedis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public List<Book> getList(int page, int pageSize) {
        return bookService.getPagedList(page, pageSize);
    }

    @GetMapping("/{isbn}")
    public Book get(@PathVariable String isbn) {
        return bookService.getById(isbn);
    }

    @PostMapping
    public Book add(@RequestBody Book book) {
        return bookService.add(book);
    }

    @PostMapping("/list")
    public List<Book> addList(@RequestBody List<Book> books) {
        return bookService.addAll(books);
    }

    @DeleteMapping("/{isbn}")
    public boolean delete(@PathVariable String isbn) {
        return bookService.remove(isbn);
    }
}
