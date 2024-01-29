package com.javarush.shevchenko.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод возвращает классы конфигурации корневого контекста
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    // Метод возвращает классы конфигурации для DispatcherServlet
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class}; // Возвращаем класс WebConfig, содержащий конфигурацию веб-приложения
    }

    // Метод возвращает URL-шаблоны, с которыми ассоциируется DispatcherServlet
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"}; // Возвращаем "/", чтобы DispatcherServlet обрабатывал все запросы к корню приложения
    }
}
