/**
 * 
 */
package com.bigsec.json;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * 
 * Date: 2014-8-20
 */
public class JsonParseTest {
	
	public static void main(String[] args) throws IOException {
        System.out.println("============序列化===========");
        Monitoring.begin();
        List<Corp> list = new ArrayList<Corp>();
        for (int i = 0; i < 100000; i++) {
            list.add(fullObject(Corp.class));
        }
        Monitoring.end("序列化生成数据");
        
        // 排除首次初始化差异
        JacksonMapper.toJson(list.get(0));
        Monitoring.begin();
        jacksonSerialize(list);
        Monitoring.end("序列化Jackson");
        
        // 排除首次初始化差异
        JSON.toJSONString(list.get(0));
        Monitoring.begin();
        fastjsonSerialize(list);
        Monitoring.end("序列化fastjson");
        
        
        System.out.println("===========反序列化===========");
        
        List<String> jsonStrList = new ArrayList<String>();
        for (Corp corp : list) {
            String str = JacksonMapper.toJson(corp);
            jsonStrList.add(str);
        }
        Monitoring.end("反序列化生成数据");
        
        // 排除首次初始化差异
    	JacksonMapper.toObj(jsonStrList.get(0), Corp.class);
        Monitoring.begin();
        jacksonUnSerialize(jsonStrList);
        Monitoring.end("反序列化Jackson");
 
        // 排除首次初始化差异
        JSON.parseObject(jsonStrList.get(0), Corp.class);
        Monitoring.begin();
        fastjsonUnSerialize(jsonStrList);
        Monitoring.end("反序列化fastjson");
        
    }
	// ---------------------------------序列化---------------------------------
    public static void jacksonSerialize(List<Corp> list) throws JsonProcessingException {
        for (Corp corp : list) {
            String str = JacksonMapper.toJson(corp);
        }
    }
    
    public static void fastjsonSerialize(List<Corp> list) {
    	for (Corp corp : list) {
    		String str = JSON.toJSONString(corp);
    	}
    }
    // ---------------------------------反序列化---------------------------------
    public static void jacksonUnSerialize(List<String> list) throws IOException {
        for (String json : list) {
        	Corp corp = JacksonMapper.toObj(json, Corp.class);
        }
    }
 
    public static void fastjsonUnSerialize(List<String> list) {
    	for (String json : list) {
    		Corp corp = JSON.parseObject(json, Corp.class);
    	}
    }
    
    /**
     * 填充一个对象（一般用于测试）
     */
    public static <T> T fullObject(Class<T> cl) {
        T t = null;
        try {
            t = cl.newInstance();
            Method methods[] = cl.getMethods();
            for (Method method : methods) {
                // 如果是set方法,进行随机数据的填充
                if (method.getName().indexOf("set") == 0) {
                    Class<?> param = method.getParameterTypes()[0];
                    if (param.equals(String.class)) {
                        method.invoke(t, getRandomString(5));
                    } else if (param.equals(Short.class)) {
                        method.invoke(t, (short) new Random().nextInt(5));
                    } else if (param.equals(Float.class)) {
                        method.invoke(t, new Random().nextFloat());
                    } else if (param.equals(Double.class)) {
                        method.invoke(t, new Random().nextDouble());
                    } else if (param.equals(Integer.class)) {
                        method.invoke(t, new Random().nextInt(10));
                    } else if (param.equals(Long.class)) {
                        method.invoke(t, new Random().nextLong());
                    } else if (param.equals(Date.class)) {
                        method.invoke(t, new Date());
                    } else if (param.equals(Timestamp.class)) {
                        method.invoke(t, new Timestamp(System.currentTimeMillis()));
                    } else if (param.equals(java.sql.Date.class)) {
                        method.invoke(t, new java.sql.Date(System.currentTimeMillis()));
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }
    
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   //生成字符串从此序列中取
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }
}
