package com.example.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {


    /**
     * ItemDto 를 다루는 RedisTemplate 을 만들어 Bean 으로 등록
     * @param connectionFactory : yml 의 redis 설정 정보를 바탕으로 만들어지는 connection
     * @return
     */
    @Bean
    public RedisTemplate<String, ItemDto> itemDtoRedisTemplate( // <String, ItemDto> : 키는 String, 값은 ItemDto 를 형상화한 무언가가 들어갈것이다
            RedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<String, ItemDto> template = new RedisTemplate<>();
        // yml redis 설정정보를 바탕으로 얻어온 db connection 을 사용하겠다
        template.setConnectionFactory(connectionFactory);

        // key 는 어떻게 직렬화/역직렬화할건지 - string 으로 할 것임
        template.setKeySerializer(RedisSerializer.string());

        // value (ItemDto) 는 어떻게 직렬화/역직렬화할 건지 - json 형식으로 할 것임
        template.setValueSerializer(RedisSerializer.json());

        return template;
    }
}
