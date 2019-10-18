package kr.co.itcen.mysite.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.web.FileUploadConfig;
import kr.co.itcen.config.web.MVCConfig;
import kr.co.itcen.config.web.MessageConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"kr.co.itcen.mysite.controller"})
@Import({MVCConfig.class,SecurityConfig.class,MessageConfig.class,FileUploadConfig.class})
public class WebConfig {//spring에서 지원하는 WebMVCConfigureAdapter라는 추상클래스를 지원을 해주는데 이걸 상속받아 웹과 관련된 설정을 해주면 된다
	
}
