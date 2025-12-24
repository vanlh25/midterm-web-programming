<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="jakarta.tags.core"%>
<!-- BEGIN CONTENT -->
<div class="col-md-9 col-sm-9 col-md-offset-1">
	<!-- Nếu muốn căn giữa -->
	<h1>Login</h1>
	<div class="content-form-page">
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<!-- Chiếm hết chiều rộng -->
				<form action="${pageContext.request.contextPath}/auth/login"
					method="post" class="form-horizontal form-without-legend"
					role="form">

					<!-- Hiển thị alert nếu có -->
					<c:if test="${not empty alert}">
						<div class="alert alert-danger">${alert}</div>
					</c:if>

					<div class="form-group">
						<label for="email" class="col-lg-3 control-label">Email <span
							class="require">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" id="email" name="email"
								value="${param.email}">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-lg-3 control-label">Password
							<span class="require">*</span>
						</label>
						<div class="col-lg-9">
							<input type="password" class="form-control" id="password"
								name="password"> <input type="checkbox" name="remember">
							Remember me
						</div>
					</div>
					<div class="row">
						<div class="col-lg-9 col-lg-offset-3 padding-left-0">
							<a href="${pageContext.request.contextPath}/auth/forgot-password">Forget
								Password?</a>
						</div>
					</div>
					<div class="row">
						<div
							class="col-lg-9 col-lg-offset-3 padding-left-0 padding-top-20">
							<button type="submit" class="btn btn-primary btn-lg">Login</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- END CONTENT -->
