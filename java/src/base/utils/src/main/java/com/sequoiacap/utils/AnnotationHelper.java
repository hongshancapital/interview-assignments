package com.sequoiacap.utils;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;

import com.sequoiacap.data.Response;

public class AnnotationHelper
{
	public static <T> T getStaticField(Class clazz, String name)
		throws Exception
	{
		Field field = clazz.getDeclaredField(name);
		
		field.setAccessible(true);
		
		return(T) field.get(null);
	}

    public static <T>
        T get(AnnotatedElement element, Class<T> annotationClazz)
    {
        Annotation[] annotations = element.getAnnotations();
        for(Annotation annotation: annotations)
        {
            if (annotationClazz.isInstance(annotation))
                return(T) annotation;
        }
        
        return null;
    }

    public static class Argument<T>
    {
        public Integer index;
        public T annotation;
        public Annotation[] annotations;
        public Class<?> type;
        public Object value;

        public <T1> T1 get(Class<T1> annotationClazz)
        {
            if (annotations == null)
                return null;

            for(Annotation annotation: annotations)
            {
                if (annotationClazz.isInstance(annotation))
                    return(T1) annotation;
            }
            
            return null;
        }
    }

    public static <T>
        Argument<T> get(Method method, Object[] args, Class<T> annotationClazz)
    {
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();

        for(int index = 0; index != annotations.length; ++index)
        {
            for(Annotation annotation: annotations[index])
            {
                if (annotationClazz.isInstance(annotation))
                {
                    Argument<T> argument = new Argument<T>();
                    
                    argument.index = index;
                    argument.annotation =(T) annotation;
                    argument.type = types[index];
                    argument.value = args[index];
                    
                    return argument;
                }
            }
        }

        return null;
    }

    public static
        Argument[] gets(Method method, Object[] args)
    {
        ArrayList<Argument> results = new ArrayList<Argument>();
        
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
    
        for(int index = 0; index != annotations.length; ++index)
        {
            Argument argument = new Argument();

            argument.index = index;
            argument.annotations = annotations[index];
            argument.type = types[index];
            argument.value = args[index];

            results.add(argument);
        }
    
        return results.toArray(new Argument[] { });
    }
    
    public static <T>
        Argument<T>[] gets(Method method, Object[] args, Class<T> annotationClazz)
    {
        ArrayList<Argument<T>> results = new ArrayList<Argument<T>>();
        
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
    
        for(int index = 0; index != annotations.length; ++index)
        {
            for(Annotation annotation: annotations[index])
            {
                if (annotationClazz.isInstance(annotation))
                {
                    Argument<T> argument = new Argument<T>();
                    
                    argument.index = index;
                    argument.annotation =(T) annotation;
                    argument.type = types[index];
                    argument.value = args[index];

                    results.add(argument);
                }
            }
        }
    
        return results.toArray(new Argument[] { });
    }
    
    public static
        Argument[] getsWithRetval(Method method, Object[] args, Object retval)
    {
        ArrayList<Argument> results = new ArrayList<Argument>();
        
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
    
        for(int index = 0; index != annotations.length; ++index)
        {
            Argument argument = new Argument();
    
            argument.index = index;
            argument.annotations = annotations[index];
            argument.type = types[index];
            argument.value = args[index];
    
            results.add(argument);
        }

        if (retval instanceof Response)
        {
            Response response =(Response) retval;
            
            Argument argument = new Argument();

            argument.index = -1;
            argument.annotations = method.getAnnotations();

            if (response.getResult() != null)
            {
                argument.type = response.getResult().getClass();
                argument.value = response.getResult();
            }

            results.add(argument);
        } else
        if (retval != null)
        {
            Argument argument = new Argument();

            argument.index = -1;
            argument.annotations = method.getAnnotations();
  
            argument.type = retval.getClass();
            argument.value = retval;

            results.add(argument);
        }

        return results.toArray(new Argument[] { });
    }
}
