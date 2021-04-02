
package com.xcf.mybatis.Tool.redis.cache;

import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xcf
 * @Date 创建时间：2021年4月2日 上午9:47:02
 */
@Slf4j
public class RedisTemplateWrapper extends RedisTemplate<String, Object> {

    private final ObjectMapper objectMapper;

    public RedisTemplateWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getValueString(String key) {

        Object o = opsForValue().get(key);
        if (o == null) {
            return null;
        } else if (o instanceof String) {
            return (String) o;
        } else {
            try {
                return objectMapper.writeValueAsString(o);
            } catch (JsonProcessingException exception) {
                //throw new BusinessException("Redis反序列化异常");
            	return null;
            }
        }
    }

    public <T> T getValueCast(String key, TypeReference<T> typeReference) {

        Object o = opsForValue().get(key);
        if (o == null) {
            return null;
        }

        return objectMapper.convertValue(o, typeReference);
    }

    public <T> T getValueCast(String key, Class<T> tClass) {

        Object o = opsForValue().get(key);
        if (o == null) {
            return null;
        }

        return  objectMapper.convertValue(o, tClass);
    }

//    public  Object getHashValueCast(String key, TypeReference<T> typeReference){
//        opsForHash().get
//    }
}
