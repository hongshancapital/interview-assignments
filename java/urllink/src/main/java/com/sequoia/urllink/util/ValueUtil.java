package com.sequoia.urllink.util;

import com.sequoia.urllink.base.model.AbstractEntity;
import com.sequoia.urllink.base.model.AbstractPojo;
import com.sequoia.urllink.constant.Global;
import lombok.extern.slf4j.Slf4j;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.hashids.Hashids;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Slf4j
public class ValueUtil {

  public final static Hashids hashids = new Hashids(Global.HASH_SALT, Global.HASH_LENGTH);

  private final static DozerBeanMapper mapper = new DozerBeanMapper();

  static {

    List<String> mappingFiles = new ArrayList();
    mappingFiles.add("dozerJdk8Converters.xml");

    mapper.setMappingFiles(mappingFiles);
    mapper.addMapping(new BeanMappingBuilder() {

      @Override
      protected void configure() {
        mapping(AbstractEntity.class, AbstractPojo.class, TypeMappingOptions.oneWay())
            .fields("id", "id", customConverter(LongToHashIdConverter.class));
        mapping(AbstractPojo.class, AbstractEntity.class, TypeMappingOptions.oneWay())
            .fields("id", "id", customConverter(HashIdToLongConverter.class));

      }
    });
  }

  public static boolean contains(Collection<String> source, String target) {

    for (String key : source) {
      if (key.equalsIgnoreCase(target)) {
        return true;
      } else {
        continue;
      }
    }

    return false;
  }

  public static boolean contains(String[] source, String target) {

    for (String key : source) {
      if (key.equalsIgnoreCase(target)) {
        return true;
      } else {
        continue;
      }
    }

    return false;
  }

  public static <S, T> T copy(S source, T target) {
    mapper.map(source, target);
    return target;
  }

  public static <S, T> T dump(Optional<S> source, Class<T> targetClass) {
    if (null == source || false == source.isPresent()) {
      return null;
    }
    return dump(source.get(), targetClass);
  }

  /**
   * 性能从高到低
   * PropertyUtils.copyProperties();
   * BeanUtils.copyProperties();
   * ValueUtil.dump()
   *
   * @param source      source
   * @param targetClass targetClass
   * @param <S>         S
   * @param <T>         T
   * @return T
   */
  public static <S, T> T dump(S source, Class<T> targetClass) {
    return mapper.map(source, targetClass);
  }

  public static <S, T> List<T> dumpList(Collection<S> sourceList, Class<T> clazz) {
    if (CollectionUtils.isEmpty(sourceList)) {
      return null;
    }
    return sourceList.stream().map(v -> dump(v, clazz)).collect(Collectors.toList());
  }

  public static Long idDecode(String source) {
    if (StringUtils.hasText(source)) {
      long[] ids = hashids.decode(source);
      if (ids.length > 0) {
        return ids[0];
      }
    }
    return null;
  }

  public static int parseStringToInteger(String string) {
    int result = 0;

    if (StringUtils.hasText(string)) {
      try {
        result = NumberFormat.getInstance().parse(string).intValue();
      } catch (ParseException e) {
        log.error("!!! Parse string fail {} !!!", string);
      }
    }

    return result;
  }

  public static Object readObjectFromByte(byte[] bytes) {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
    Object object = null;
    try {
      in = new ObjectInputStream(bis);
      object = in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      log.error("!!! Read object from bytes failed !!");
    } finally {
      if (null != in) {
        try {
          in.close();
        } catch (IOException e) {
          log.error("!!! Close InputStream failed !!");
        }
      }
      if (null != bis) {
        try {
          bis.close();
        } catch (IOException e) {
          log.error("!!! Close ObjectInput failed !!");
        }
      }
    }

    return object;
  }

  public static byte[] writeObjectToByte(Object object) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutput out = null;
    byte[] bytes = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(object);
      bytes = bos.toByteArray();
    } catch (IOException e) {
      log.error("!!! Write object to bytes failed !!");
    } finally {
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
          log.error("!!! Close OutputStream failed !!");
        }
      }
      if (null != bos) {
        try {
          bos.close();
        } catch (IOException e) {
          log.error("!!! Close ObjectOutput failed !!");
        }
      }
    }

    return bytes;
  }

  static public class HashIdToLongConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass) {
      if (Objects.isNull(sourceFieldValue)) {
        return null;
      }
      existingDestinationFieldValue = idDecode((String) sourceFieldValue);
      return existingDestinationFieldValue;
    }
  }

  static public class LongToHashIdConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass) {
      if (Objects.isNull(sourceFieldValue)) {
        return null;
      }
      existingDestinationFieldValue = hashids.encode((Long) sourceFieldValue);
      return existingDestinationFieldValue;
    }
  }


}
