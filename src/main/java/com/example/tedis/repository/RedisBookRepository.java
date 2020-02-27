package com.example.tedis.repository;

import com.example.tedis.model.Book;
import io.lettuce.core.internal.LettuceLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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

    private final HashOperations<String, String, Object> hashOperations;
    private final ZSetOperations<String, Object> zSetOperations;
    private final Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(false);

    @Autowired
    public RedisBookRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public List<Book> getList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int stop = page * pageSize - 1;

        List<Object> keys = Optional.ofNullable(zSetOperations.range(INDEX_KEY, start, stop))
                .map(LettuceLists::newList)
                .orElse(new ArrayList<>());

        return keys.stream()
                .map(key -> hashOperations.entries(KEY_PREFIX + key))
                .map(entry -> (Book) jackson2HashMapper.fromHash(entry))
                .collect(Collectors.toList());
    }

    @Override
    public Book get(String isbn) {
        Map<String, Object> entries = hashOperations.entries(KEY_PREFIX + isbn);

        if (entries.isEmpty()) {
            return null;
        }

        return (Book) jackson2HashMapper.fromHash(entries);
    }

    @Override
    public Book add(Book book) {
        zSetOperations.add(INDEX_KEY, book.getIsbn(), FIXED_SCORE);
        hashOperations.putAll(KEY_PREFIX + book.getIsbn(), jackson2HashMapper.toHash(book));

        return get(book.getIsbn());
    }

    @Override
    public List<Book> addAll(List<Book> books) {
        for (Book book : books) {
            zSetOperations.add(INDEX_KEY, book.getIsbn(), FIXED_SCORE);
            hashOperations.putAll(KEY_PREFIX + book.getIsbn(), jackson2HashMapper.toHash(book));
        }

        return books;
    }

    public Book remove(String isbn) {
        Book book = get(isbn);
        zSetOperations.remove(INDEX_KEY, isbn);
        hashOperations.delete(KEY_PREFIX + isbn, jackson2HashMapper.toHash(book).keySet().toArray());

        return book;
    }
}
