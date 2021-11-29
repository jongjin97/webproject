<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


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
	<body class="is-preload">
		<div id="page-wrapper">

			<!-- Header -->
				<%@ include file="./common/header.jsp" %>

			<!-- Main -->
				<div id="main" class="wrapper style1">
					<div class="container">
						<header class="major">
							<h2>로그인</h2>
							<p>로그인해주세요</p>
						</header>
						<!-- Form -->
							<section>
								<form method="post" action="/Woosuk/controller/LoginCheck">
									<div class="row gtr-uniform gtr-50">
										<div class="col-6 col-6-xsmall">
											<input type="text" name="name" id="name" value="" placeholder="아이디" />
											<div id="name_error" class="error"></div>
										</div>
										<div class="col-6 col-6-xsmall">
											<input type="password" name="pass" id="pass" value="" placeholder="비밀번호" />
											<div id="password_error" class="error"></div>
										</div>
										<div class="col-12">
											<ul class="actions">
												<li><input id="login_btn" type="submit" value="Sign In" class="primary" /></li>
												<li><input id="reset_btn" type="reset" value="Cancel" /></li>
											</ul>
										</div>
									</div>
								</form>
							</section>

					</div>
				</div>

			<!-- Footer -->
				<%@ include file="./common/footer.jsp" %>

		</div>

		<!-- Scripts -->
			<%@ include file="./common/script.jsp" %>
			<script type="text/javascript">
				window.addEventListener("DOMContentLoaded", function(){
					
					<%
					if(request.getParameter("message") != null && request.getParameter("message").equals("ID")){
					%>
						setErrorMessage("name_error", "아이디를 찾을 수 없습니다.");
					<%
					}else if(request.getParameter("message") != null && request.getParameter("message").equals("PASS")){
					%>
						setErrorMessage("password_error", "비밀번호를 확인해주세요.");
					<%
					}
					%>
					
					var id = document.getElementById("name");
					var pass = document.getElementById("pass");
					
					id.addEventListener("keyup", function(){
						if(id.value == "" || id.value.length == 0) {
							setErrorMessage("name_error", "아이디를 입력해주세요.")
						} else {
							removeErrorMessage("name_error");
						}
					});
					
					pass.addEventListener("keyup", function(){
						if(pass.value == "" || pass.value.length == 0) {
							setErrorMessage("password_error", "비밀번호를 입력해주세요.");
						} else {
							removeErrorMessage("password_error");
						}
					});
					
					document.getElementById("login_btn").addEventListener("click", function(evt){
						if(id.value == "" || id.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("name_error", "아이디를 입력해주세요.")
						} else {
							removeErrorMessage("name_error");
						}
						
						if(pass.value == "" || pass.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("password_error", "비밀번호를 입력해주세요.");
						} else {
							removeErrorMessage("password_error");
						}
					});
					
					document.getElementById("reset_btn").addEventListener("click", function(evt){
						evt.preventDefault();
						document.location="/Woosuk/index.html";
					});
				});
			</script>
	</body>
</html>