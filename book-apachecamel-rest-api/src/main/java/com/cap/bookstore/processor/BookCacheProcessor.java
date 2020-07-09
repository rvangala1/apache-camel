package com.cap.bookstore.processor;

import com.cap.bookstore.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@SuppressWarnings("ALL")
@Component
public class BookCacheProcessor {

    @Autowired
    private RedisTemplate<String, Book> redisTemplate;

    public void saveBookToCache(@ExchangeProperty("isbn") String cacheKey,
                                @ExchangeProperty("propBookRequest") Book book) {
        redisTemplate.opsForValue().set(cacheKey, book);
        // Set the expiry time for the key to be 23:59:59 of current date
        redisTemplate.expireAt(cacheKey, Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));

    }

    public Book getBookFromCache(@ExchangeProperty("isbn") String cacheKey) {
        if (redisTemplate.hasKey(cacheKey)) {
           return redisTemplate.opsForValue().get(cacheKey);
        }
        else
            return null;
    }

}
