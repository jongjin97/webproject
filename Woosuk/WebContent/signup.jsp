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

	d		<!-- Header -->
				<%@ include file="./common/header.jsp" %>

			<!-- Main -->
				<div id="main" class="wrapper style1">
					<div class="container">
						<header class="major">
							<h2>회원가입</h2>
							<p>가입해주세요</p>
						</header>
						<!-- Form -->
							<section>
								<form id="frm" method="post" action="/Woosuk/controller/Signup">
									<div class="row gtr-uniform gtr-50">
										<div class="col-6 col-12-xsmall">
											<input type="text" name="name" id="name" value="" placeholder="아이디" />
											<div id="name_error" class="error"></div>
										</div>
										<div class="col-6 col-12-xsmall">
											<input type="email" name="email" id="email" value="" placeholder="이메일" />
											<div id="email_error" class="error"></div>
										</div>
										<div class="col-6 col-12-xsmall">
											<input type="password" name="pass1" id="pass1" value="" placeholder="비밀번호" />
											<div id="pass1_error" class="error"></div>
										</div>
										<div class="col-6 col-12-xsmall">
											<input type="password" name="pass2" id="pass2" value="" placeholder="비밀번호 확인" />
											<div id="pass2_error" class="error"></div>
										</div>
										<div class="col-12">
											<select name="job" id="job">
												<option value="">- 직업 -</option>
												<option value="1">학생</option>
												<option value="2">프로그래머</option>
												<option value="3">서버엔지니어</option>
												<option value="4">디자이너</option>
											</select>
											<div id="job_error" class="error"></div>
										</div>
										<div class="col-4 col-12-medium">
											<input type="radio" id="priority-low" name="priority" value="MAN" checked>
											<label for="priority-low">남</label>
										</div>
										<div class="col-4 col-12-medium">
											<input type="radio" id="priority-normal" name="priority" value="WOMAN"> 
											<label for="priority-normal">여</label>
										</div>
										<div class="col-12">
											<textarea name="message" id="message" placeholder="자기소개" rows="6"></textarea>
										</div>
										<%
											if(user == null){
										%>
										<div class="col-6 col-12-medium">
											<input type="checkbox" id="copy" name="copy">
											<label for="copy">개인정보수집에 동의합니다.</label>
											<a href="javascript:popup('/Woosuk/agree.jsp');">(개인정보수집 및 이용)</a>
											<div id="copy_error" class="error"></div>
										</div>
										<%
											}
										%>
										<div class="col-12">
											<ul class="actions">
												<li><input id="signup_btn" type="submit" value="가입" class="primary" /></li>
												<li><input id="cancel_btn" type="reset" value="취소" /></li>
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
					var name = document.getElementById("name");
					var email = document.getElementById("email");
					var pass1 = document.getElementById("pass1");
					var pass2 = document.getElementById("pass2");
					var job = document.getElementById("job");
					<%
						if(user == null){
					%>
						var copy = document.getElementById("copy");
					<%
						}
					%>
					name.addEventListener("keyup", function(){
						if(name.value == "" || name.value.length == 0) {
							setErrorMessage("name_error", "아이디를 입력해주세요.");
						} else {
							var pattern = /[^a-zA-Z0-9]/;
							if(pattern.test(name.value)) {
								setErrorMessage("name_error", "아이디는 영어와 숫자 조합만 가능합니다.");
							} else {
								removeErrorMessage("name_error");
							}
						}
					});
					email.addEventListener("keyup", function(){
						var pattern = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+$/;
						if(!pattern.test(email.value)) {
							setErrorMessage("email_error", "이메일 형식으로 입력해주세요.");
						} else {
							removeErrorMessage("email_error");
						}
					});
					pass1.addEventListener("keyup", function(){
						document.getElementById("pass1_error").className = "error";
						var level = passwordLevel(pass1.value);
						if(level == 0) {
							setErrorMessage("pass1_error", "비밀번호를 입력해주세요.");
						} else if(level == 1) {
							setErrorMessage("pass1_error", "비밀번호는 영어,숫자,특수문자(~!@#$%^&*()_+|<>?:{})만 가능합니다.");
						} else if(level == 2) {
							setErrorMessage("pass1_error", "너무 쉬운 암호입니다.");
						} else if(level == 3) {
							setErrorMessage("pass1_error", "더 강력한 조합이 필요합니다.");
						} else if(level == 4) {
							document.getElementById("pass1_error").className = "non_error";
							setErrorMessage("pass1_error", "안전한 암호입니다.");
						}
						
						if(pass1.value != pass2.value) {
							setErrorMessage("pass2_error", "비밀번호를 확인해 주세요.")
						} else {
							removeErrorMessage("pass2_error");
						}
					});
					pass2.addEventListener("keyup", function(){
						if(pass1.value != pass2.value) {
							setErrorMessage("pass2_error", "비밀번호를 확인해 주세요.")
						} else {
							removeErrorMessage("pass2_error");
						}
					});
					job.addEventListener("change", function(){
						if(job.value == "") {
							setErrorMessage("job_error", "직업을 선택하세요.");
						} else {
							removeErrorMessage("job_error");
						}
					});
					<%
						if(user == null){
					%>
					copy.addEventListener("change", function(){
						if(!copy.checked) {
							setErrorMessage("copy_error", "개인정보수집에 동의해야 회원가입이 가능합니다.");
						} else {
							removeErrorMessage("copy_error");
						}
					});
					<%
						}
					%>
					
					document.getElementById("signup_btn").addEventListener("click", function(evt){
						if(name.value == "" || name.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("name_error", "아이디를 입력해주세요.");
						} else {
							var pattern = /[a-zA-Z0-9]/;
							if(!pattern.test(name.value)) {
								evt.preventDefault();
								setErrorMessage("name_error", "아이디는 영어와 숫자 조합만 가능합니다.");
							}
						}
						
						var pattern = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]+$/;
						if(!pattern.test(email.value)) {
							evt.preventDefault();
							setErrorMessage("email_error", "이메일 형식으로 입력해주세요.");
						}
						
						document.getElementById("pass1_error").className = "error";
						var level = passwordLevel(pass1.value);
						if(level == 0) {
							evt.preventDefault();
							setErrorMessage("pass1_error", "비밀번호를 입력해주세요.");
						} else if(level == 1) {
							evt.preventDefault();
							setErrorMessage("pass1_error", "비밀번호는 영어,숫자,특수문자(~!@#$%^&*()_+|<>?:{})만 가능합니다.");
						} else if(level == 2) {
							evt.preventDefault();
							setErrorMessage("pass1_error", "너무 쉬운 암호입니다.");
						} else if(level == 3) {
							evt.preventDefault();
							setErrorMessage("pass1_error", "더 강력한 조합이 필요합니다.");
						} else if(level == 4) {
							document.getElementById("pass1_error").className = "non_error";
							setErrorMessage("pass1_error", "안전한 암호입니다.");
						}
						
						if(pass1.value != pass2.value) {
							evt.preventDefault();
							setErrorMessage("pass2_error", "비밀번호를 확인해 주세요.")
						}
						
						if(job.value == "") {
							evt.preventDefault();
							setErrorMessage("job_error", "직업을 선택하세요.");
						}
						<%
							if(user == null){
						%>
						if(!copy.checked) {
							evt.preventDefault();
							setErrorMessage("copy_error", "개인정보수집에 동의해야 회원가입이 가능합니다.");
						}
						<%
							}
						%>
						
						
					
					});
					<%
						if(user != null){
					%>
						document.getElementById("name").value="<%=user.getId()%>";
						document.getElementById("name").readOnly = true;
						document.getElementById("email").value="<%=user.getEmail()%>";
						document.getElementById("job").value=<%=user.getJob()%>;
					<%
						if(user.getGender().equals("MAN")){
					%>
						document.getElementById("priority-low").checked=true;
					<%
						}else{
					%>
						document.getElementById("priority-normal").checked=true;
					<%
						}
					%>
						document.getElementById("message").value="<%=user.getIntroduction()%>";
						document.getElementById("signup_btn").value="수정";
						document.getElementById("frm").action = "/Woosuk/controller/updateUser";
						document.getElementById("cancel_btn").addEventListener("click",function(evt){
							evt.prenentDefault();
							history.back(-1);
						});
					<%
						}
					%>
				});
			</script>
	</body>
</html>