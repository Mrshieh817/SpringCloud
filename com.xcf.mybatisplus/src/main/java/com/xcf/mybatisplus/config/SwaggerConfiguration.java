package com.xcf.mybatisplus.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/** 
* @author xcf 
* @Date 创建时间：2021年5月24日 下午2:44:52 
*/
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
	
	
	@Value("${project.name}")
	private String projectName;
	
	@Value("${project.description}")
	private String description;
 
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build().globalOperationParameters(globalOperationParameters()).securitySchemes(securitySchemes());
        return docket;
    }
    
    private List<Parameter> globalOperationParameters() {

        return Collections.singletonList(new ParameterBuilder()
            .name("Authorization")
            .modelRef(new ModelRef("String"))
            .parameterType("header")
            .required(false)
            .build());
    }
 
    private ApiInfo apiInfo() {
        Contact contact = new Contact("pengsn", "", "");
        return new ApiInfoBuilder().title(projectName).
        description(description).contact(contact).version("2.0").build();
    }
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = Lists.newArrayListWithExpectedSize(1);
        apiKeyList.add(new ApiKey("Authorization", "[GATEWAY]认证参数", "header"));
        return apiKeyList;
    }
}
