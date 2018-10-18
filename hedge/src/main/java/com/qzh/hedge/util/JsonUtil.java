package com.qzh.hedge.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class JsonUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    public static ObjectMapper getMapper() {
        return JsonUtil.SingletonHolder.mapper;
    }

    public static String toJsonString(Object pojo) {
        if(pojo == null) {
            return null;
        } else {
            try {
                return getMapper().writeValueAsString(pojo);
            } catch (IOException var2) {
                LOG.error("pojo parse  json string error", var2);
                return null;
            }
        }
    }

    public static JsonNode parseJson(String input) {
        return parseJson(input, true);
    }

    public static JsonNode parseJson(String input, boolean isPrintError) {
        if(input == null) {
            return null;
        } else {
            try {
                return getMapper().readTree(input);
            } catch (IOException var3) {
                if(isPrintError) {
                    LOG.error("json processing error,input: " + input, var3);
                }

                return null;
            }
        }
    }

    public static JsonNode getJsonNodefromStream(InputStream in) {
        try {
            return getMapper().readTree(in);
        } catch (IOException var2) {
            LOG.error("read file error", var2);
            return null;
        }
    }

    public static <T> T jsonToObject(String jsonString, Class<T> valueType) {
        if(StringUtils.hasText(jsonString)) {
            try {
                return getMapper().readValue(jsonString, valueType);
            } catch (IOException var3) {
                LOG.error("json to object failed", var3);
            }
        }

        return null;
    }

    public static <T> T jsonToObject(String jsonStr, Class<?> collectionClass, Class... elementClass) {
        if(!StringUtils.hasText(jsonStr)) {
            return null;
        } else {
            JavaType javaType = getMapper().getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClass);

            try {
                return getMapper().readValue(jsonStr, javaType);
            } catch (IOException var5) {
                LOG.error("json to object failed", var5);
                return null;
            }
        }
    }

    public static ObjectNode createObjectNode() {
        return getMapper().createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return getMapper().createArrayNode();
    }

    public static <T> T convert(Object pojo, Class<T> target) {
        return pojo == null?null:getMapper().convertValue(pojo, target);
    }

    public static <T> T convert(Object pojo, Class<?> collectionClass, Class... elementClass) {
        if(pojo == null) {
            return null;
        } else {
            JavaType javaType = getMapper().getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClass);
            return getMapper().convertValue(pojo, javaType);
        }
    }

    private static class SingletonHolder {
        private static final ObjectMapper mapper = new ObjectMapper();

        private SingletonHolder() {
        }

        static {
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE));
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }
}
