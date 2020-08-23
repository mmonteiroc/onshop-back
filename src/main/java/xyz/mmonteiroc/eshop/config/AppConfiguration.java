package xyz.mmonteiroc.eshop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.mmonteiroc.eshop.handler.LogedUserFilter;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 04/06/2020
 * Package: com.mmonteiroc.addesso.config
 * Project: addesso
 */
@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Bean
    public LogedUserFilter getTokenFilter() {
        return new LogedUserFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(getTokenFilter()).addPathPatterns("/**").excludePathPatterns("/auth/**", "/photos/**");
    }
}