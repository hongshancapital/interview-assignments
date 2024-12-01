package com.zy.url.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
	private BeanUtil() {}
	
	public static Map<String, Object> toMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
		if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));
        }    
  
        return map;  
    }
	
	public static Object toBean(Map<String, Object> map, Class<?> beanClass) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
		if(map == null) return null;
		Object result = beanClass.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(result.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(result, map.get(property.getName()));   
            }  
        }
		return result;
	}
}
