package com.interview.assignment.pojo;

import com.interview.assignment.domain.Application;
import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.domain.ShortCodeClick;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.model.IdCounter;
import com.interview.assignment.model.Segment;
import com.interview.assignment.request.APIRequest;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.APIResponse;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class PojoTest {

  @Test
  public void testPojo() {
    Set<Class<?>> classes = new HashSet<>();
    classes.add(APIRequest.class);
    classes.add(Application.class);
    classes.add(ShortCodeClick.class);
    classes.add(ShortCode.class);
    classes.add(ShortCodeQueryResponse.class);
    classes.add(ShortCodeGenerateResponse.class);
    classes.add(APIResponse.class);
    classes.add(ShortCodeGenerateRequest.class);
    classes.add(ShortCodeQueryRequest.class);
    classes.add(GeneratedId.class);
    classes.add(DurationType.class);
    classes.add(CodeType.class);
    classes.add(IdCounter.class);
    classes.add(Segment.class);
    classes.add(DurationType.LIMITED_TIME.getClass());
    classes.add(DurationType.PERMANENT.getClass());
    classes.add(CodeType.URL_REDIRECT.getClass());

    for (Class<?> clazz : classes) {
      Object obj = newInstance(clazz);
      if (null != obj) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
          try {
            if (method.getParameterCount() == 0) {
              method.invoke(obj);
            } else if (method.getParameterCount() == 1) {
              method.invoke(null);
            }
          } catch (Exception e) {
            // ignore
          }
        }
      }
    }
  }

  private Object newInstance(Class<?> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      return null;
    }
  }
}
