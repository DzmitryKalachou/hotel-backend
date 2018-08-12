package com.example.hotel.configurations;

import com.example.hotel.security.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new StringHttpMessageConverter());
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }
}
