package com.example.tedis.repository;

import com.example.tedis.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BookRepository {
    private static final String KEY_PREFIX = "book:";

    private final HashOperations<String, String, Object> hashOperations;
    private final Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(false);

    @Autowired
    public BookRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Book get(String isbn) {
        Map<String, Object> entries = hashOperations.entries(KEY_PREFIX + isbn);

        if (entries.isEmpty()) {
            return null;
        }

        return (Book) jackson2HashMapper.fromHash(entries);
    }

    public Book add(Book book) {
        hashOperations.putAll(KEY_PREFIX + book.getIsbn(), jackson2HashMapper.toHash(book));
        return get(book.getIsbn());
    }

    public Book remove(String isbn) {
        Book book = get(isbn);
        hashOperations.delete(KEY_PREFIX + isbn, jackson2HashMapper.toHash(book).keySet().toArray());

        return book;
    }
}
