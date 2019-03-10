package com.vodaphone.codechallenge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

  @Bean
  public Docket productApi() {
      return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.vodaphone.codechallenge.controller"))
          .paths(PathSelectors.regex("/api.*"))
          .build()
          .apiInfo(apiInfo())
          .pathMapping("/");
  }
  
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui.html")
      .addResourceLocations(getStaticLocations());

    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
    
    super.addResourceHandlers(registry);
  }
  
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Vodafone Malta Code Challenge ")
        .description("REST Web-Service responsible of maintaining a database of mobile numbers, that are assigned to clients, along with some related information")
        .version("1.0")
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
        .contact(new Contact("Joabe Costa", "https://www.linkedin.com/in/joabecosta/en", "joabetc@hotmail.com"))
        .build();
  }
  
  private String[] getStaticLocations() {

    String[] result = new String[5];
    result[0] = "/";
    result[1] = "classpath:/META-INF/resources/";
    result[2] = "classpath:/resources/";
    result[3] = "classpath:/static/";
    result[4] = "classpath:/public/";

    return result;
}
}
