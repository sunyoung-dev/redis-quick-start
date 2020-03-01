package com.example.tedis.repository;

import com.example.tedis.model.Book;
import io.lettuce.core.internal.LettuceLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RedisBookRepository implements BookRepository {
    private static final String KEY_PREFIX = "book:";
    private static final String INDEX_KEY = "book:idx";
    private static final double FIXED_SCORE = 0;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    private final BoundZSetOperations<String, Object> boundZSetOperations;
    private final Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(false);

    @Autowired
    public RedisBookRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.boundZSetOperations = redisTemplate.boundZSetOps(INDEX_KEY);
    }

    @Override
    public List<Book> findByRange(int start, int stop) {
        List<Object> keys = Optional.ofNullable(boundZSetOperations.range(start, stop))
                .map(LettuceLists::newList)
                .orElse(new ArrayList<>());

        return keys.stream()
                .map(key -> hashOperations.entries(KEY_PREFIX + key))
                .map(entry -> (Book) jackson2HashMapper.fromHash(entry))
                .collect(Collectors.toList());
    }

    @Override
    public Book find(String isbn) {
        Map<String, Object> entries = hashOperations.entries(KEY_PREFIX + isbn);

        if (entries.isEmpty()) {
            return null;
        }

        return (Book) jackson2HashMapper.fromHash(entries);
    }

    @Override
    public Book save(Book book) {
        boundZSetOperations.add(book.getIsbn(), FIXED_SCORE);
        hashOperations.putAll(KEY_PREFIX + book.getIsbn(), jackson2HashMapper.toHash(book));

        return find(book.getIsbn());
    }

    @Override
    public List<Book> saveAll(List<Book> books) {
        for (Book book : books) {
            boundZSetOperations.add(book.getIsbn(), FIXED_SCORE);
            hashOperations.putAll(KEY_PREFIX + book.getIsbn(), jackson2HashMapper.toHash(book));
        }

        return books;
    }

    public boolean remove(String isbn) {
        if (boundZSetOperations.score(isbn) == null) {
            return false;
        }

        boundZSetOperations.remove(isbn);
        redisTemplate.delete(KEY_PREFIX + isbn);

        return true;
    }
}
