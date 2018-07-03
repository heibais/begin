package com.iamgpj.begin.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iamgpj.begin.core.common.CommonTree;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Description: 高频方法集合类
 * @author: gpj
 * @Create: 2018/6/8 21:41
 */
@Slf4j
public class ToolUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 获取随机位数的字符串
     *
     * @author GPJ
     * @Date 2017/8/24 14:09
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取随机位数的数字
     *
     * @author GPJ
     * @Date 2017/8/24 14:09
     */
    public static String getRandomNum(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 对象属性复制
     * @param source 源数据
     * @param clazz 目标类
     */
    public static <T> T map(Object source, Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            if (source != null) {
                BeanUtils.copyProperties(source, instance);
            }
            return  instance;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("【对象转换】 信息={}", e);
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
    }

    /**
     * 对象属性复制
     * @param source 源数据
     * @param clazz 目标类
     */
    public static <T> List<T> mapList(List<?> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        return source.stream().map(item -> map(item, clazz)).collect(Collectors.toList());
    }

    /**
     * 将list集合转为树形结构
     * @param list 带转换的集合
     * @param <T> 泛型
     * @return
     */
    public static <T extends CommonTree> List<T> list2Tree(List<T> list) {
        // 筛选顶级栏目 与 子级栏目
        List<T> rootList = new ArrayList<>();
        List<T> subList = new ArrayList<>();
        for (T item : list) {
            if (item.getPid().equals(0)) {
                rootList.add(item);
            } else {
                subList.add(item);
            }
        }
        format2tree(rootList, subList);
        return rootList;
    }

    private static  <T extends CommonTree> void format2tree(List<T> rootList, List<T> subList) {
        for (T root : rootList) {
            List<T> nextList = new ArrayList<>();
            for (T sub : subList) {
                if (root.getId().equals(sub.getPid())) {
                    nextList.add(sub);
                }
            }
            if (!CollectionUtils.isEmpty(nextList)) {
                root.setChildren(nextList);
                format2tree(nextList, subList);
            }
        }
    }

    /**
     * 对象转为字符串
     * @param object
     * @return
     */
    public static String object2String(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("【json转换】错误 信息={}", e);
            throw new BeginException(ExceptionEnum.JSON_PARSE_ERROR);
        }
    }

    /**
     * 字符串转为对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T string2Object(String str, Class<T> clazz) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        try {
            return objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.error("【json转换】错误 信息={}", e);
            throw new BeginException(ExceptionEnum.JSON_PARSE_ERROR);
        }
    }
}
