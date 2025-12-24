<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- BEGIN TOP BAR -->
<div class="pre-header">
	<div class="container">
		<div class="row">
			<!-- BEGIN TOP BAR LEFT PART -->
			<div class="col-md-6 col-sm-6 additional-shop-info">
				<ul class="list-unstyled list-inline">
					<li><i class="fa fa-phone"></i><span>+0856113439</span></li>
				</ul>
			</div>
			<!-- END TOP BAR LEFT PART -->

			<!-- BEGIN TOP BAR MENU -->
			<div class="col-md-6 col-sm-6 additional-nav">
				<ul class="list-unstyled list-inline pull-right">
					<!-- Các nút menu -->
					<li><a href="${pageContext.request.contextPath}/user/home">Trang Chủ</a></li>
					<li><a href="${pageContext.request.contextPath}/user/book/list">Sản phẩm</a></li>
					<c:if
						test="${sessionScope.account != null && sessionScope.account.isAdmin}">
						<li><a href="#">Trang quản trị</a></li>
					</c:if>
					<!-- Login / Logout -->
					<li><c:choose>
							<c:when test="${sessionScope.account == null}">
								<a href="${pageContext.request.contextPath}/auth/login">Login</a> |
                                <a
									href="${pageContext.request.contextPath}/auth/register">Register</a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/user/profile">${sessionScope.account.fullname}</a> |
                                <a
									href="${pageContext.request.contextPath}/auth/logout">Logout</a>
							</c:otherwise>
						</c:choose></li>
				</ul>
			</div>
			<!-- END TOP BAR MENU -->
		</div>
	</div>
</div>
<!-- END TOP BAR -->


<!-- BEGIN HEADER -->
<div class="header">
	<div class="container">
		<a class="site-logo" href=${pageContext.request.contextPath}/home><img
			src="${URL}assets/frontend/layout/img/logos/logo-shop-red.png"
			alt="Metronic Shop UI"></a> <a href="javascript:void(0);"
			class="mobi-toggler"><i class="fa fa-bars"></i></a>
	</div>
</div>
<!-- Header END -->