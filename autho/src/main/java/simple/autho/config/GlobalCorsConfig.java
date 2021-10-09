package simple.autho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class GlobalCorsConfig implements WebMvcConfigurer {
    // //添加到容器中管理
    // @Bean
    // public CorsFilter corsFilter() {
    //     CorsConfiguration config = new CorsConfiguration();
    //       config.addAllowedOrigin("*");
    //       config.setAllowCredentials(true);
    //       config.addAllowedMethod("*");
    //       config.addAllowedHeader("*");
    //       config.addExposedHeader("*");
 
    //     UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
    //     configSource.registerCorsConfiguration("/**", config);
 
    //     return new CorsFilter(configSource);
    // }

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
    }
}