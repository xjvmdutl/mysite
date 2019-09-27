<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>no</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list }' var='vo' varStatus='status'>
						<tr>
							<td>${(index-(status.index))}
							</td>
							
							<c:choose>
							<c:when test="${vo.depth>0 }">
							<td style='padding-left:${40*vo.depth}px'>
								<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" style='padding-left:${40*vo.depth}px'/>
								<c:if test="${vo.status ne 'delete'}">
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no }">${vo.title }</a>
								</c:if>
								<c:choose>
										<c:when test="${vo.status eq 'modify'}">[수정]</c:when>
										<c:when test="${vo.status eq 'insert'}">[생성]</c:when>
										<c:otherwise>[삭제된 글입니다.]</c:otherwise>
								</c:choose>		
							</td>
							</c:when>
							<c:otherwise>
							<td>
								<c:if test="${vo.status ne 'delete'}">
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no }">${vo.title }</a>
								</c:if>
									<c:choose>
										<c:when test="${vo.status eq 'insert'}">[생성]</c:when>
										<c:when test="${vo.status eq 'modify'}">[수정]</c:when>
										<c:otherwise>[삭제된 글입니다.]</c:otherwise>
									</c:choose>
								
							</td>
							</c:otherwise>
							</c:choose>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:if test="${authUser.no eq vo.user_no }">
							<td><a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no }" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${pagenumber>1 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagenumber}">◀</a></li>
					</c:if>
						<c:forEach begin='1' end='5' step='1' var='i'>
							<c:choose>
								<c:when test="${(showpage mod 5) eq (i mod 5)}">
								<c:if test="${count>(pagenumber+i-1)*5}">
								<li class="selected">
										<a href="${pageContext.servletContext.contextPath }/board?page=${pagenumber+i }&kwd=${kwd}">${pagenumber+i }</a>
								</li>
								</c:if>
								</c:when>
								<c:otherwise>
								<c:if test="${count>(pagenumber+i-1)*5}">
									<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagenumber+i }&kwd=${kwd}">${pagenumber+i }</a></li>	
								</c:if>
								</c:otherwise>
							</c:choose>			
						</c:forEach>
	 					<c:if test="${count>(pagenumber+5)*5}">
						<li><a href="${pageContext.servletContext.contextPath }/board?page=${pagenumber+6}">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				<c:if test="${not empty authUser}">
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/write id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>