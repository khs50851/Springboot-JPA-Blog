<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="username">Username</label> <input type="text" class="form-control" placeholder="Enter Username" id="username">
		</div>
		
		<div class="form-group">
			<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group">
			<label for="email">Email</label> <input type="email" class="form-control" placeholder="Enter email" id="email">
		</div>
		
		
	</form>
	
	<button id="btn-save" class="btn btn-primary">회원가입 완료</button>
	
</div>
<!-- /라고 하면 바로 static 폴더를 찾아감 -->
<script src="/blog/js/user.js">
</script>
<%@ include file="../layout/footer.jsp"%>