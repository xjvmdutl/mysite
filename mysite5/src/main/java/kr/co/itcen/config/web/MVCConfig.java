package kr.co.itcen.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
/*
 * 1) View Resolver
 * 2) Default Servlet Handler 
 * 3) Message Converter
 */
@Configuration
@EnableWebMvc//MVC와 관련된 설정이 하위로 내려갔기때문에 위에서 할 필요가 없다
public class MVCConfig extends WebMvcConfigurerAdapter {
	//view Resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver= new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true);
		return viewResolver;
	}
	//Default Servlet Handler 
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	//Message Converter
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
				.indentOutput(true)
				.dateFormat(new SimpleDateFormat("yyyy-mm-dd"))
				.modulesToInstall(new ParameterNamesModule());//parameter-name 디펜던시 필요
		MappingJackson2HttpMessageConverter converter
			=new MappingJackson2HttpMessageConverter(builder.build());
		//AJAX
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application","json",Charset.forName("UTF-8"))));
		
		return converter;
	}
	//Simplehttp
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text","html",Charset.forName("UTF-8"))));
		return converter;
	}
	//converter를 오버라이드 해주어야한다.
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(stringHttpMessageConverter());
	}
}
