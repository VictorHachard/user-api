package com.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
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

	public static RunEnum runEnum = RunEnum.PRODUCTION;

	/**
	 * args need to be like -run prod, -run dev, -run test-run
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i += 2) {
			switch (args[i]) {
				case "-run":
					if (args[i+1].equals("prod")) {
						runEnum = RunEnum.PRODUCTION;
					} else if (args[i+1].equals("dev")) {
						runEnum = RunEnum.DEVELOPMENT;
					} else if (args[i+1].equals("test-run")) {
						runEnum = RunEnum.TEST_RUN;
					}
					break;
			}
		}
		if (runEnum == RunEnum.TEST_RUN) {
			try {
				System.exit(SpringApplication.exit(SpringApplication.run(RunApplication.class, args)));
			} catch (Exception e) {
				System.exit(1);
			}
		} else {
			SpringApplication.run(RunApplication.class, args);
		}
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