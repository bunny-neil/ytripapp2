package com.ytripapp.api.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.ytripapp.api.support.HttpHeaderLocaleResolver;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class WebMvcConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Bean
    MappingJackson2HttpMessageConverter converter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        objectMapper.registerModule(hibernate5Module);
        Jackson2ObjectMapperBuilder.json().configure(objectMapper);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Bean
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:/messages");
        return messageSource;
    }

    @Bean
    @Override
    public LocaleResolver localeResolver() {
        return new HttpHeaderLocaleResolver();
    }
}
