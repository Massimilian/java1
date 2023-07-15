package settings;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("servlets")
@EnableWebMvc
public class WebContextConfig implements WebMvcConfigurer {
    // метод, переопределив который мы объясняем, где лежат отображения и в каком они формате
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

//    второй вариант того же самого:
//    @Bean
//    public ViewResolver getViewResolver() {
//        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
//        irvr.setPrefix("/WEB-INF/jsp/");
//        irvr.setSuffix(".jsp");
//        return irvr;
//    }
}
