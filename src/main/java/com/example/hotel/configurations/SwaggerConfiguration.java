package com.example.hotel.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;


@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfiguration {


    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.example.hotel.controllers"))
            .paths(PathSelectors.any())
            .build()
            .forCodeGeneration(true)
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Hotel API",
            "", "0.0.1", "", new Contact("dzmitry.kalachou", "", ""), "", "", Collections.emptyList());

    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "Authorization", ",");
    }

}
