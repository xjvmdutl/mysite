package kr.co.itcen.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.itcen.mysite.dto.JSONResult;

//<context:component-scan base-package="kr.co.itcen.mysite.exception" />
// component-scan base-package 에 경로 추가
@ControllerAdvice
public class GlobalExceptionHandler {
   
   private static final Log Log = LogFactory.getLog(GlobalExceptionHandler.class );
   
   // 모든 Exception을 다 받아서 처리하고 싶다. @ExceptionHandler(Exception.class)
   @ExceptionHandler(Exception.class)
   public void handlerException(
         HttpServletRequest request, 
         HttpServletResponse response, 
         Exception e) throws Exception {
      
      // 1. 로깅
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      Log.error(errors.toString());
      
      //2-1 요청구분
      //만약 JSON요청인 경우는 application/json
      //만약 HTML요청인 경우에는 text/html
      //만약 jpeg(image)요청인 경우는 image/jpeg
      String accept=request.getHeader("accept");//브라우저가 헤더에 accept라고 보냈다
      if(accept.matches(".*application/json.*")) {//application/json이 들어가는 모든문자
    	  //3.JSON응답
    	  response.setStatus(HttpServletResponse.SC_OK);
    	  JSONResult jsonResult = JSONResult.fail(errors.toString());
    	  String result=new ObjectMapper().writeValueAsString(jsonResult);//제이슨 객체를 받으면 이것을 string 으로 바꾸어주겠다.
    	  																	//controller가 아니기 떄문에 빈설정이 안되어 있어 이를 직접해주어야 한다.
    	  OutputStream os = response.getOutputStream();
    	  os.write(result.getBytes("UTF-8"));
    	  os.close();
      }else {//기존하던요청
    	  // 2. 안내페이지
          request.setAttribute("uri", request.getRequestURI());
          request.setAttribute("exception", errors.toString());
          request
             .getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
             .forward(request, response);
      }
   }
   
}