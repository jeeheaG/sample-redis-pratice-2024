package com.example.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("item") // redis 에서 사용될 키
public class Item implements Serializable {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer price;
}
