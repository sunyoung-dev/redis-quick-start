package com.example.tedis.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash("person")
public class Person {
    @Id
    private String id;
    private String name;
    private String age;
    private String birthDate;
    private String phone;
}
