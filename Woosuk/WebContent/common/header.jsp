<%@page import="kr.ac.woosuk.computereng.models.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	UserDTO user = (UserDTO)session.getAttribute("user");
    %>
    
				<header id="header">
					<h1 id="logo"><a href="/Woosuk/index.jsp">Landed</a></h1>
					<nav id="nav">
						<ul>
							<li><a href="/Woosuk/index.jsp">Home</a></li>
							<li>
								<a href="/Woosuk/controller/Board">Board</a>
							</li>
							
							<%
								if(user != null){
							%>
								<li><%=user.getId() %>님</li>
								<li><a href="/Woosuk/controller/modifyUser" class="button primary">회원정보수정</a></li>
								<li><a href="/Woosuk/controller/signout" class="button">로그아웃</a></li>
							<%
								}else{
							%>
								<li><a href="/Woosuk/signin.jsp" class="button primary">로그인</a></li>
								<li><a href="/Woosuk/signup.jsp" class="button primary">회원가입</a></li>
							<%
								}
							%>
						</ul>
					</nav>
				</header>
				
		