package com.ctgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Date;

@SpringBootApplication
@ServletComponentScan
@EnableRedisHttpSession
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Converter<Long, Date> addNewConvert() {
		return new Converter<Long, Date>() {
			@Override
			public Date convert(Long source) {
				Date date = null;
				try {
					date = new Date(source);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return date;
			}
		};
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
		return (container -> {
//			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
//			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
//			container.addErrorPages(error401Page, error404Page, error500Page);
			container.addErrorPages(error404Page);
		});
	}
}
