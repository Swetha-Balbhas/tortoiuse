package com.om.springboot.services;

import com.om.springboot.config.SecurityConfig;
import com.om.springboot.controller.TestController;
import com.om.springboot.mappers.TestMapper;
import com.om.springboot.security.CustomUserDetailsService;
import com.om.springboot.service.TestUserService;
import org.mybatis.spring.annotation.MapperScan;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackageClasses = {TestController.class, TestUserService.class, SecurityConfig.class, CustomUserDetailsService.class})
@MapperScan("com.om.springboot.mappers")
@EnableAsync
@EnableJpaRepositories(basePackages = {"com.om.springboot.controller.ui.repository"})

@EntityScan(basePackages = {"com.om.springboot.model.user"})
public class ServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx){
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
				if(beanName.equals("testMapper")){
					System.out.println(ctx.getBean(beanName));
					((TestMapper)ctx.getBean(beanName)).findAllTest().stream().forEach(
							r -> System.out.println(r.getTestid())
					);
				}
			}
		};
	}
}
