<%@page import="kr.ac.woosuk.computereng.models.BoardDTO"%>
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
		<%
			BoardDTO dtos = (BoardDTO)request.getAttribute("dto");
		%>
	</head>
	
	<body class="is-preload">
		<div id="page-wrapper">

			<!-- Header -->
				<%@ include file="./common/header.jsp" %>

			<!-- Main -->
				<div id="main" class="wrapper style1">
					<div class="container">
						<header class="major">
							<h2>게시글 작성</h2>
						</header>

						<!-- Form -->
							<section>
								<form method="post" action="/Woosuk/controller/Write_board" enctype = "multipart/form-data">
									<div class="row gtr-uniform gtr-50">
										<div class="col-12">
											<input type="text" name="title" id="title" value="" placeholder="제목" />
											<div id="title_error" class="error"></div>
										</div>
										<div class="col-12">
											<input type="text" name="subtitle" id="subtitle" value="" placeholder="소제목" />
										</div>
										<div class="col-12">
											<input type="file" name="file" id="file" value="" placeholder="이미지파일" />
											<div id="file_error" class="error"></div>
										</div>
										<div class="col-12">
											<textarea name="contents" id="contents" placeholder="내용을 입력하세요" rows="6"></textarea>
											<div id="contents_error" class="error"></div>
										</div>
										<div class="col-12">
											<ul class="actions">
												<li><a href="board.jsp" class="button primary">목록</a></li>
												<li></li>
												<li></li>
												<li><input id="save_btn" type="submit" value="저장" class="primary" /></li>
												<li><input type="reset" value="작성취소" /></li>
												
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
					var title = document.getElementById("title");
					var file = document.getElementById("file");
					var contents = document.getElementById("contents");
					
					title.addEventListener("keyup", function(){
						if(title.value == "" || title.value.length == 0) {
							setErrorMessage("title_error", "제목을 입력해주세요.");
						} else {
							removeErrorMessage("title_error");
						}
					});
					
					file.addEventListener("change", function(){
						if(file.value == "" || file.value.length == 0) {
							setErrorMessage("file_error", "파일을 업로드해주세요.");
						} else {
							var fileext = file.value;
							fileext = fileext.slice(fileext.lastIndexOf(".")+1).toLowerCase();
							if(fileext != "jpg" && fileext != "jpeg" && fileext != "gif" && fileext != "bmp" && fileext != "png") {
								setErrorMessage("file_error", "이미지 파일만 업로드 할 수 있습니다.");
							} else {
								removeErrorMessage("file_error");	
							}
						}
					});
					
					contents.addEventListener("keyup", function(){
						if(contents.value == "" || contents.value.length == 0) {
							setErrorMessage("contents_error", "내용을 작성해주세요.");
						} else {
							removeErrorMessage("contents_error");
						}
					});
					
					document.getElementById("save_btn").addEventListener("click", function(evt){
						if(title.value == "" || title.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("title_error", "제목을 입력해주세요.");
						}
						
						if(file.value == "" || file.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("file_error", "파일을 업로드해주세요.");
						} else {
							var fileext = file.value;
							fileext = fileext.slice(fileext.lastIndexOf(".")+1).toLowerCase();
							if(fileext != "jpg" && fileext != "jpeg" && fileext != "gif" && fileext != "bmp" && fileext != "png") {
								evt.preventDefault();
								setErrorMessage("file_error", "이미지 파일만 업로드 할 수 있습니다.");
							}
						}
						
						if(contents.value == "" || contents.value.length == 0) {
							evt.preventDefault();
							setErrorMessage("contents_error", "내용을 작성해주세요.");
						}
					});
					<%
					if(dtos != null){
					%>
						document.getElementById("title").value = "<%=dtos.getTitle()%>";
						document.getElementById("subtitle").value = "<%=dtos.getSubTitle()%>";
						document.getElementById("contents").value = "<%=dtos.getContents()%>";
						
						document.getElementById("save_btn").value = "수정";
						document.getElementById("reset").value = "수정 취소";
						document.getElementById("frm").action = "/Woosuk/controller/updateBoard?id=<%=dtos.getId()%>";
						document.getElementById("reset").addEventListener("click", function(evt) {
							evt.preventDefault();
							history.back(-1);
						});
					<%
					}
					%>
				});
			</script>
	</body>
</html>