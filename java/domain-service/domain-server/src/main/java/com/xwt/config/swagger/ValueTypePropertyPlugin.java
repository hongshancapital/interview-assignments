package com.xwt.config.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiModelProperty;
import org.raven.commons.data.ValueType;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValueTypePropertyPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {

        Optional<ApiModelProperty> annotation = Optional.absent();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }
        if (context.getAnnotatedElement().isPresent() && context.getAnnotatedElement().get() instanceof Field) {

            final Class<?> rawPrimaryType = ((Field) context.getAnnotatedElement().get()).getType();
            //过滤得到目标类型
            if (annotation.isPresent() && ValueType.class.isAssignableFrom(rawPrimaryType)) {

                //固定设置为int类型
                final ResolvedType resolvedType = context.getResolver().resolve(int.class);
                ValueType[] values = new ValueType[0];

                if (rawPrimaryType.isEnum()) {
                    values = (ValueType[]) rawPrimaryType.getEnumConstants();
                    final List<String> displayValues = Arrays.stream(values).map(item -> item.getValue().toString()).collect(Collectors.toList());
                    final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
                    context.getBuilder().allowableValues(allowableListValues).type(resolvedType);
                } else {
                    context.getBuilder().type(resolvedType);
                }
            }
        }

    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
