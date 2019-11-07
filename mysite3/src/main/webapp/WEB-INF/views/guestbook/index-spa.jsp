<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset{
	float: none;
	text-align:center
}
.ui-dialog .ui-dialog-buttonpane button {
	margin-left:10px;
	margin-right:auto;
}
#dialog-message p {
	padding:20px 0;
	font-weight:bold;
	font-size:1.0em;
}
#dialog-delete-form p {
	padding:10px;
	font-weight:bold;
	font-size:1.0em; 
}
#dialog-delete-form p.error {
	color: #f00;
}
#dialog-delete-form input[type="password"] {
	padding:5px;
	border:1px solid #888;
	outline: none;
	width: 180px;
}
</style>
<script>
   // jQuery plug-in
   (function($){
      $.fn.hello = function(){
         console.log("hello #" + $(this).attr("id"));
      } 
   })(jQuery);
   (function($){//함수 하나당 하나가 규약이다
	      $.fn.flash = function(){
	        $(this).click(function(){
	        	var isBlink=false;
	        	var $that =$(this);
	        	setInterval(function(){
					$that.css("backgroundColor",isBlink ? "#f00" : "#aaa");
					isBlink=!isBlink;
				},1000);
	        });
		} 
	})(jQuery);
</script>
<script type="text/javascript">
	var startNo=0;
	var isEnd=false;
	var messageBox = function(title,message,callback){//닫히고 나서 할 동작을 callback 적어 준다.
		$("#dialog-message p").text(message);
		$("#dialog-message")
			.attr("title", title)
			.dialog({
				modal:true,
				buttons:{
					"확인":function(){
						$(this).dialog('close');//dialog닫기	
					}//버튼이름:실행코드
				},
				close:function(){
					callback && callback();//callback이 null이 아니면 실행시켜 주어라
					
				}
			});
	}
	//template
	var listItemTemplate = new EJS({//li 하나만 렌더링
		url:"${pageContext.request.contextPath }/assets/js/ejs-templates/list-item-template.ejs",
	});
	var listTemplate = new EJS({//li 하나만 렌더링
		url:"${pageContext.request.contextPath }/assets/js/ejs-templates/list-template.ejs",
	});
	/*
	var render = function(vo,mode) {//mode는 앞에 달지 뒤에달지
	//실제로는 template 라이브러리를 사용한다(html rendering library)
	//ejs, underscore, mustache
		var html = 
			"<li data-no='"+vo.no+"'>"+
			"<strong>"+vo.name+"</strong>"+
			"<p>"+vo.contents.replace(/\n/gi,"<br>")+"</p>"+//정규 표현식
			"<strong></strong>"+
			"<a href='' data-no='"+vo.no+"'>삭제</a> "+
			"</li>";
		if(mode){
			$("#list-guestbook").append(html);		
		}else{
			$("#list-guestbook").prepend(html);
		}
		//$("#list-guestbook")[mode ? "append" | "prepend"](html);	
	}
	*/
	function fetchList(){	
		$.ajax({
			url:"${pageContext.servletContext.contextPath}/api/guestbook/list/"+startNo,
			type:"get",//알아서 자바객체로 바꿔라,post방식 JSON객체//보내는 데이터 타입
			dataType:'json',//받을떄 Json형식으로 받는다
			data:"",
			success:function(response){
				//startPage++;
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				//detect end
				if(response.data.length == 0){
					isEnd= true; 
					$("btn-next").prop("disabled",true);//프로퍼티 설정
					return;
				}
				//rendering
				var html =listTemplate.render(response);
				$("#list-guestbook").append(html);
				//$.each(response.data,function(index,vo){
					//render(vo,true);
				
				//});
				startNo=$("#list-guestbook li").last().data("no") || 0;//data-no를 뽑아온다.
			},
			error:function(xhr,status,e){
				console.error(status+":"+e);
			}
		});
	}
	$(function(){
		var dialogDelete = $("#dialog-delete-form").dialog({
			autoOpen:false,
			width:300,
			height:170,
			modal: true,
			
			buttons:{
				"삭제":function(){//AJAX통신필요
					var no =$("#no-delete").val();
					var password = $("#password-delete").val();
					
					$.ajax({
			               url: "${pageContext.request.contextPath }/api/guestbook/"+no,
			               type: "delete",
			               dataType: 'json',
			               data:"password="+password,
			               success: function(response){
			                  if(response.result != "success"){
			                     console.error(response.message);
			                     return;
			                  }
			                  console.log(password);
			                  if(response.data != -1){
			                	  $("#list-guestbook li[data-no="+response.data+"]").remove();
			                	  dialogDelete.dialog('close');
			                	  
			                  }
			                  
			               },
			               error: function(xhr, status, e){
			                  console.error(status + ":" + e);
			               }
			            });
				},
				"취소":function(){
					$(this).dialog('close');
				}
			},
			close:function(){
				$("#no-delete").val("");
				$("password-delete").val("");
			},
		});//다이얼 로그 객체 선언
		$("#btn-next").click(fetchList);
		$("#add-form").submit(function(event){
			event.preventDefault();//폼방식을 막아야한다

			var vo = {};
			//Validation(client side)는 생략->이것은 양쪽 다 해야한다.
			vo.name = $("#input-name").val();
			if(vo.name == ""){
				//alert("이름은 필수 입력 항목입니다.");
				messageBox("방명록 남기기","이름은 필수 입력 항목입니다.",function(){
					$("#input-name").focus();
				});
				return;
			}
			vo.password = $("#input-password").val();
			if(vo.password == ""){
				messageBox("방명록 남기기","비밀번호는  필수 입력 항목입니다.",function(){
					$("#input-password").focus();
				});
				return;
			}
			vo.contents = $("#tx-content").val();
			if(vo.contents == ""){
				messageBox("글 남기기","내용은  필수 입력 항목입니다.",function(){
					$("#tx-content").focus();
				});
				return;
			}
			
			//console.log($.param(vo));
			//ajax 통신
			$.ajax({
				url:"${pageContext.servletContext.contextPath}/api/guestbook/add",
				async:true,//false시 동기
				type:"POST",
				contentType:'application/json',//알아서 자바객체로 바꿔라,post방식 JSON객체//보내는 데이터 타입
				dataType:'json',//받을떄 Json형식으로 받는다
				data:JSON.stringify(vo),//vo객체를 string로 만들어준다
				success:function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					//rendering
					//render(response.data);
					var html =listItemTemplate.render(response.data);
					$("#list-guestbook").prepend(html);
					//form reset
					$("#add-form")[0].reset();
				},
				error:function(xhr,status,e){
					console.error(status+":"+e);
				}
			});
		
		});
		$(window).scroll(function() {
			var $window = $(this);
			var windowHeight = $window.height();
			var scrollTop = $window.scrollTop();
			var documentHeight = $(document).height();
			
			if(scrollTop + windowHeight +10 > documentHeight){
				fetchList();
			}
		});
		
		//LiveEvent: 미래에 생길거 같은애(현재 존재하지 않는 element)의 이벤트 핸들러를 미리 bind하는 작업
		//delegation(위임)
		$(document).on('click','#list-guestbook li a',function(event){//도큐먼트에 주어야 한다(항상 존재하기 떄문에), 도큐먼트에게 이벤트를 위임한다.
			event.preventDefault();//ajax통신을 할거이기 떄문에 사용
			
			$("#no-delete").val($(this).data("no"));
			
			dialogDelete.dialog('open');
			//다이얼 로그 띄우기
		
			
		})
		
		//처음 리스트 가지고 오기
		fetchList();
		
		//jquery plugin test
		$("#btn-next").hello();
		$("#btn-next").flash();
	});	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<button id="btn-next">next</button>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="no-delete" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="방명록남기기" style="display:none">
  				<p>이름은 필수 입력 항목입니다.</p>
			</div>		
						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>