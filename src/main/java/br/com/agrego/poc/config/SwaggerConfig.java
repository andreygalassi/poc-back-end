package br.com.agrego.poc.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuração do swagger para a documentação dos endpoints
 * https://www.treinaweb.com.br/blog/documentando-uma-api-spring-boot-com-o-swagger/
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()//RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint")
					.apis(RequestHandlerSelectors.basePackage("br.com.agrego.poc.endpoint"))
//					.apis(RequestHandlerSelectors.any())
					.paths(regex("/.*"))
					.build()
//				.globalOperationParameters(Collections.singletonList(new ParameterBuilder()
//					.name("Authorization")
//					.description("Bearer token")
//					.modelRef(new ModelRef("string"))
//					.parameterType("header")
//					.required(true)
//					.build()))
				.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
				.securityContexts(Arrays.asList(securityContext()))
				.apiInfo(metaData());
		

//        .useDefaultResponseMessages(false)
//        .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
//        .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
//        .securityContexts(Arrays.asList(securityContext()));
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("POC RESTful com OAuth2.")
				.description("implementação de acesso a endpoints com token rest usando oauth2.")
				.version("0.0.2")
				.contact(new Contact("Andrey", "", ""))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/license/LICENSE-2.0")
				.build();
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.ant("/v1/**"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("ADMIN", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Token Access", authorizationScopes));
	}
}
