package com.homo.core.utils.serial;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

;

/**
 * 基于jackson序列化和反序列化处理器
 * 适用于大对象
 * @apiNote https://www.w3cschool.cn/article/28650020.html
 * @apiNote https://wenku.baidu.com/view/1e8c35f54a649b6648d7c1c708a1284ac850057e.html
 */
@Log4j2
public class JacksonSerializationProcessor implements HomoSerializationProcessor{
    public static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)//如果启用，如果发现未知属性，Jackson 将抛出异常。如果禁用将忽略这样的字段。（默认true）
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);//如果没有像 getter 这样的字段的访问器，则会抛出异常。（默认true）
    static {

        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
            .withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
        );
    }
    @Override
    public byte[] writeByte(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        }catch (JsonProcessingException e){
            log.error("JacksonSerializationProcessor writeByte failed! ",e);
            throw new RuntimeException("serial failed! " + e.getMessage());
        }

    }

    @Override
    public String writeString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JacksonSerializationProcessor writeByte failed! ",e);
            throw new RuntimeException("serial failed! " + e.getMessage());
        }
    }

    @Override
    public <T> T readValue(byte[] obj, Class<T> clazz) {
        try {
            return mapper.readValue(obj,clazz);
        } catch (IOException e) {
            log.error("JacksonSerializationProcessor readValue failed! ",e);
            throw new RuntimeException("unSerial failed! " + e.getMessage());
        }
    }

    @Override
    public <T> T readValue(byte[] obj, HomoTypeReference<T> reference) {
        try {
            return mapper.readValue(obj,mapper.constructType(reference.type));
        } catch (IOException e) {
            log.error("JacksonSerializationProcessor readValue failed! ",e);
            throw new RuntimeException("unSerial failed! " + e.getMessage());
        }
    }

    @Override
    public <T> T readValue(String obj, Class<T> clazz) {
        try {
            return mapper.readValue(obj,clazz);
        } catch (JsonProcessingException e) {
            log.error("JacksonSerializationProcessor readValue failed! ",e);
            throw new RuntimeException("unSerial failed! " + e.getMessage());
        }
    }

    @Override
    public <T> T readValue(String obj, HomoTypeReference<T> reference) {
        try {
            return mapper.readValue(obj,mapper.constructType(reference.type));
        } catch (JsonProcessingException e) {
            log.error("JacksonSerializationProcessor readValue failed! ",e);
            throw new RuntimeException("unSerial failed! " + e.getMessage());
        }
    }
}
