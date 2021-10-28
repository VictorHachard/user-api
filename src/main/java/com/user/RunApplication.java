package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //https://www.baeldung.com/spring-boot-security-autoconfiguration
@EnableSwagger2
public class RunApplication {

	public static boolean PRODUCTION = true;

	/**
	 * args need to be like -run prod ou -run dev
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i += 2) {
			switch (args[i]) {
				case "-run":
					if (args[i+1].equals("prod")) {
						PRODUCTION = true;
					} else if (args[i+1].equals("dev")) {
						PRODUCTION = false;
					}
					break;
			}
		}
		SpringApplication.run(RunApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.user.controller")).build();
	}

	@Bean
	public TokenInterceptor myCustomHandlerInterceptor() {
		return new TokenInterceptor();
	}

	@Bean
	public WebMvcConfigurerAdapter adapter() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(myCustomHandlerInterceptor());
			}
		};
	}

}