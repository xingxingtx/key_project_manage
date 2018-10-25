package com.aorise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author:Huangdong
 * @Desicription:
 * @Date:Created in 2018/6/26.
 * @Modified By:
 */
@Configuration
public class Swagger {

    @Bean
    public Docket RecruitApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Recruit")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("")//默认访问根目录
                .select()
                .build()
                .apiInfo(RecruitApinfo());

    }


    private ApiInfo RecruitApinfo() {
        return new ApiInfoBuilder()
                .title("Api for Key_project_manage  Server")
                .description("Key_project_manage Server's REST API")
                .version("V1.0")
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("shenzhiwei", "", "zhiwei.shen@aorise.org"))
                .license("GPL")
                .licenseUrl("")
                .build();
    }
}
