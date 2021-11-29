<%@page import="kr.ac.woosuk.computereng.models.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.ac.woosuk.computereng.models.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	BoardDao dao = new BoardDao();
	ArrayList<BoardDTO> alist = dao.indexList();
%>
<!DOCTYPE HTML>
<!--
	Landed by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<%@ include file="./common/title.jsp" %>
	</head>
	<body class="is-preload landing">
		<div id="page-wrapper">

			<!-- Header -->
				<%@ include file="./common/header.jsp" %>

			<!-- Banner -->
				<section id="banner">
					<div class="content">
						<header>
							<h2>웹프로그래밍 기말과제....</h2>
							<p>인생..<br />
						</header>
						<span class="image"><img src="images/bird.jpg" alt="" /></span>
					</div>
					<a href="#one" class="goto-next scrolly">Next</a>
				</section>
		<%if(alist.size() >0) {%>
			<!-- One -->
				<section id="one" class="spotlight style1 bottom">
					<span class="image fit main"><img src="/Woosuk/controller/imageLoad?id=<%=alist.get(0).getId()%>" alt="" /></span>
					<div class="content">
						<div class="container">
							<div class="row">
								<div class="col-4 col-12-medium">
									<header>
										<h2><%=alist.get(0).getTitle() %></h2>
										<p><%=alist.get(0).getSubTitle() %></p>
									</header>
								</div>
								<div class="col-4 col-12-medium">
									<p><%=alist.get(0).getContents() %></p>
									<p><%=alist.get(0).getFormatDate() %></p>
								</div>
							</div>
						</div>
					</div>
					<a href="#two" class="goto-next scrolly">Next</a>
				</section>
		<%} %>
		<%if (alist.size() > 1) { %>
			<!-- Two -->
				<section id="two" class="spotlight style2 right">
					<span class="image fit main"><img src="/Woosuk/controller/imageLoad?id=<%=alist.get(1).getId() %>" alt="" alt="" /></span>
					<div class="content">
						<header>
							<h2><%= alist.get(1).getTitle() %></h2>
							<p><%= alist.get(1).getSubTitle() %></p>
						</header>
						<p><%= alist.get(1).getContents() %></p>
						<p><%= alist.get(1).getFormatDate() %></p>
						<ul class="actions">
							<li><a href="board.jsp" class="button">Learn More</a></li>
						</ul>
					</div>
					<a href="#three" class="goto-next scrolly">Next</a>
				</section>
		<%} %>
		<%if(alist.size() > 2) { %>
			<!-- Three -->
				<section id="three" class="spotlight style3 left">
					<span class="image fit main bottom"><img src="/Woosuk/controller/imageLoad?id=<%=alist.get(2).getId() %>" alt="" alt="" /></span>
					<div class="content">
						<header>
							<h2><%= alist.get(2).getTitle() %></h2>
							<p><%= alist.get(2).getSubTitle() %></p>
						</header>
						<p><%= alist.get(2).getContents() %></p>
						<p><%= alist.get(2).getFormatDate() %></p>
						<ul class="actions">
							<li><a href="board.jsp" class="button">Learn More</a></li>
						</ul>
					</div>
					<a href="#four" class="goto-next scrolly">Next</a>
				</section>
				<section id="five" class="wrapper style2 special fade">
					<div class="container">
					</div>
				</section>
		<%} %>

			<!-- Footer -->
				<%@ include file="./common/footer.jsp" %>

		</div>

		<!-- Scripts -->
			<%@ include file="./common/script.jsp" %>

	</body>
</html>