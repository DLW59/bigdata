package com.dlw.bigdata.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc 序列化工具类 用protostuff
 */
public class SerializationUtil2 {



    /**
     * 序列化单个对象
     * @return
     */
    public static <T> byte[] serialize(T obj) throws IOException {
        if (obj == null) {
            throw new RuntimeException("序列化对象为空!");
        }
        //通过对象的类构建对应的schema；
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        //使用LinkedBuffer分配一块默认大小的buffer空间；
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protostuff = null;
        try {
            //使用给定的schema将对象序列化为一个byte数组，并返回。
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    /**
     * 反序列化单个对象
     * @return
     */
    public static <T> T deserialize(byte[] bytes,Class<T> tClass) {
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            //实例化一个类的对象
            instance = tClass.newInstance();
        } catch (InstantiationException  e1) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e1);
        } catch(IllegalAccessException e2){
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e2);
        }
        //通过对象的类构建对应的schema
        Schema<T> schema = RuntimeSchema.getSchema(tClass);
        //使用给定的schema将byte数组和对象合并，并返回。
        ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
        return instance;
    }

    /**
     * 序列化多个对象
     * @return
     */
    public static <T> byte[] serializes(List<T> list) throws IOException {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("序列化对象列表(" + list + ")参数异常!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protostuff = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
            protostuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表(" + list + ")发生异常!", e);
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return protostuff;
    }

    /**
     * 反序列化多个对象
     * @return
     */
    public static <T> List<T> deserializes(byte[] bytes,Class<T> tClass) {
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }

        Schema<T> schema = RuntimeSchema.getSchema(tClass);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(bytes),
                    schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!", e);
        }
        return result;
    }
}
