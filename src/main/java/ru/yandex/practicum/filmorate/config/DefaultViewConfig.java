package ru.yandex.practicum.filmorate.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author Oleg Khilko
 */

@Configuration
public class DefaultViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
