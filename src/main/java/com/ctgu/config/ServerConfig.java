//package com.ctgu.config;
//
//import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
//@Configuration
//public class ServerConfig {
//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
//        //ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//        //ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//        //container.addErrorPages(error401Page, error404Page, error500Page);
//        factory.addErrorPages(error404Page);
//        return factory;
//    }
//}