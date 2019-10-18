package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//1.
@Target({ElementType.METHOD,ElementType.TYPE})//유효범위를 어디다 설정하는지에 따라 클래스나, method등 여러군대에 붙힐수 있다.
@Retention(RetentionPolicy.RUNTIME)//런타임때 이걸 떈다.
public @interface Auth {//어노테이션 인터페이스는 @를 붙힌다.
	//public enum Role{USER,ADMIN};
	
	//public Role role() default Role.USER;
	public String value() default "USER";//입력안하면 USER야
	
	//public int test() default 1;
}
