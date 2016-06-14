package mirna.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan(basePackages = "mirna.api.controller")
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

//  @Bean
//  public WebContentInterceptor webContentInterceptor() {
//    WebContentInterceptor interceptor = new WebContentInterceptor();
//    interceptor.setCacheSeconds(0);
//    interceptor.setUseExpiresHeader(true);
//    interceptor.setUseCacheControlHeader(true);
//    interceptor.setUseCacheControlNoStore(true);
//
//    return interceptor;
//  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/libs/**").addResourceLocations("/libs/");
    registry.addResourceHandler("/app/**").addResourceLocations("/app/");
    registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
  }

//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(webContentInterceptor());
//  }

  @Bean
  public RelProvider relProvider() {
    return new EvoInflectorRelProvider();
  }
}
