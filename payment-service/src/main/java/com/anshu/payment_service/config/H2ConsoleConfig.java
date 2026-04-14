package com.anshu.payment_service.config;

import jakarta.servlet.Servlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2ConsoleServlet() {
        ServletRegistrationBean<Servlet> registrationBean = new ServletRegistrationBean<>(createH2ConsoleServlet(),
                "/h2-console/*");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    private Servlet createH2ConsoleServlet() {
        try {
            Class<?> servletClass = Class.forName("org.h2.server.web.JakartaWebServlet");
            return (Servlet) servletClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to create the H2 console servlet", exception);
        }
    }
}
