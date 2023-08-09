package com.tuke.fei.kpi.isvote.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "isvote.swagger-enabled")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tuke.fei.kpi.isvote"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IS Vote - API")
                .description("REST API documentation for IS Vote project")
                .termsOfServiceUrl(" ")
                .contact(new Contact("Dominik Horvath", "", "dominik.horvath@student.tuke.sk"))
                .license(" ")
                .licenseUrl(" ")
                .version("0.0.1")
                .build();
    }
}
