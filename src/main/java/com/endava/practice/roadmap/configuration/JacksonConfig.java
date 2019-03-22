package com.endava.practice.roadmap.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final Optional<HttpMessageConverter<?>> converterFound = converters.stream()
            .filter(c -> c instanceof MappingJackson2HttpMessageConverter).findFirst();

        if (converterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) converterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }    
}