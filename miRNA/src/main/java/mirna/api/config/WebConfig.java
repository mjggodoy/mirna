package mirna.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
@EnableWebMvc
//@ComponentScan(basePackages = "es.uma.khaos.pabs.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

//  @Bean
//  public InternalResourceViewResolver getInternalResourceViewResolver() {
//    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//    resolver.setPrefix("/WEB-INF/views/");
//    resolver.setSuffix(".html");
//    return resolver;
//  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public WebContentInterceptor webContentInterceptor() {
    WebContentInterceptor interceptor = new WebContentInterceptor();
    interceptor.setCacheSeconds(0);
    interceptor.setUseExpiresHeader(true);
    interceptor.setUseCacheControlHeader(true);
    interceptor.setUseCacheControlNoStore(true);

    return interceptor;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/libs/**").addResourceLocations("/libs/");
    registry.addResourceHandler("/app/**").addResourceLocations("/app/");
    registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(webContentInterceptor());
  }
}
