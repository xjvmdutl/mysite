package kr.co.itcen.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
      
      // 2. 안내페이지
      request.setAttribute("uri", request.getRequestURI());
      request.setAttribute("exception", errors.toString());
      request
         .getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
         .forward(request, response);
   }
   
}