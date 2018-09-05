package org.kjtc.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisObjectSerializer  implements RedisSerializer<Object> {

    private Converter<Object,byte[]> serializer = new SerializingConverter();
    private Converter<byte[],Object> deserializer = new DeserializingConverter();
    private final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null){
            return EMPTY_ARRAY;
        }

        try {
            return serializer.convert(o);
        }catch (Exception e){
            return EMPTY_ARRAY;
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length == 0){
            return null;
        }
        try {
            return deserializer.convert(bytes);
        }catch (Exception e){
            throw new SerializationException("Can not deserializer",e);
        }
    }
}
