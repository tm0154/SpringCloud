package server.commom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * The type Json util.
 *
 * @Description
 * @Author tieminPan
 * @Date 2018 /10/13 22:29
 * @Param
 * @return
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 常规json转换为map对象工具类
     *
     * @param jsonStr the json str
     * @return map map
     * @throws
     */
    public static Map<String, Object> jsonToMap(String jsonStr) {
        JSONObject itemJSONObj = JSONObject.parseObject(jsonStr);
        Map<String, Object> itemMap = JSONObject.toJavaObject(itemJSONObj, Map.class);
        return itemMap;
    }

    /**
     * 将map转换为json字符串
     *
     * @param map the map
     * @return string string
     */
    public static String mapToJson(Map map) {
        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }


    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 对象序列化为Sting
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return string string
     */
    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对象序列化为Sting
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return string string
     */
    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * String 转变为Obj
     *
     * @param <T>   the type parameter
     * @param str   the str
     * @param clazz the clazz
     * @return t t
     */
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String 转变为Obj
     *
     * @param <T>           the type parameter
     * @param str           the str
     * @param typeReference the type reference
     * @return t t
     */
    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String 转变为Obj
     *
     * @param <T>             the type parameter
     * @param str             the str
     * @param collectionClass the collection class
     * @param elementClasses  the element classes
     * @return t t
     */
    public static <T> T stringToObj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(obj);
    }
}
