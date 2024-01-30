package com.javarush.shevchenko.config;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
@ComponentScan("com.javarush.shevchenko")
public class WebConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final PropertiesService propertiesService;

    // Метод для создания Thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix(propertiesService.getResolverPrefix());
        resolver.setSuffix(propertiesService.getResolverSuffix());
        resolver.setCacheable(propertiesService.getCacheable());
        return resolver;
    }

    // Метод для создания и настройки движка шаблонов Thymeleaf
    @Bean
    public SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    // для поиска view
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(this.engine());
        resolver.setCharacterEncoding(propertiesService.getEncoding());
        resolver.setCache(propertiesService.getCacheable());
        return resolver;
    }

    // Метод для добавления обработчиков ресурсов (статических файлов)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/views/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/views/js/");
    }
}
