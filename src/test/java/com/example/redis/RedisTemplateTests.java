package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 자동으로 만들어져 있음

    @Test
    public void stringOpsTest() {
        // <String, String> : 키와 값 모두 자바의 String으로 제공해서 만든 데이터에 대해
        // stringRedisTemplate.opsForValue() : redisTemplate 의 설정을 바탕으로 redis 문자열 조작을 할 클래스를 얻어옴
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        ops.set("simpleKey", "simpleValue");
        System.out.println(ops.get("simpleKey")); // 결과 : simpleValue

        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        setOps.add("hobbies", "game");
        setOps.add("hobbies", "reading", "running", "sleeping");
        System.out.println(setOps.size("hobbies")); // 결과 : 4

        stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS);
        stringRedisTemplate.delete("simpleKey");
    }

    @Autowired
    private RedisTemplate<String, ItemDto> itemDtoRedisTemplate;

    @Test
    public void itemRedisTemplateTest() {
        // 만들어뒀던 ItemDto 를 다루는 RedisTemplate 으로부터 데이터를 다룰 클래스를 얻어옴
        ValueOperations<String, ItemDto> ops = itemDtoRedisTemplate.opsForValue();

        // <String, ItemDto> 와 같이 키는 String, 값은 ItemDto 자료형을 씀
        ops.set("my:keyboard", ItemDto.builder()
                .name("Mechanical Keyboard")
                .price(300000)
                .description("Expensive 😢")
                .build());
        System.out.println(ops.get("my:keyboard"));

        ops.set("my:mouse", ItemDto.builder()
                .name("mouse mice")
                .price(100000)
                .description("Expensive 😢")
                .build());
        System.out.println(ops.get("my:mouse"));

        /*
        결과 :
        ItemDto(name=Mechanical Keyboard, description=Expensive 😢, price=300000)
        ItemDto(name=mouse mice, description=Expensive 😢, price=100000)
         */
    }
}
