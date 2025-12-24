<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="manager-taskbar mb-4">
    <!-- Home -->
    <a href="${pageContext.request.contextPath}/admin/home"
       class="btn btn-secondary">Home</a>
    <!-- Profile luôn active -->
    <a href="${pageContext.request.contextPath}/admin/profile"
       class="btn btn-primary active">Profile</a>
</div>

<div class="manager-content mt-5">
    <h2>Thông tin cá nhân</h2>
    <form action="<c:url value='/admin/profile'/>" method="post" class="text-left col-md-6 offset-md-3">
        <div class="form-group">
            <label for="fullName">Họ và tên:</label>
            <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullname}" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-group">
            <label for="phone">Số điện thoại:</label>
            <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" required>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Cập nhật</button>
    </form>
</div>

<style>
.manager-taskbar .btn {
    margin-right: 10px;
    transition: all 0.2s ease;
    font-size: 16px;
    padding: 10px 20px;
}
.manager-taskbar .btn.active {
    transform: translateY(-2px);
    box-shadow: 0 3px 8px rgba(0,0,0,0.25);
}
.manager-taskbar .btn:hover {
    opacity: 0.9;
}

.manager-content h2 {
    color: #333;
    margin-bottom: 30px;
}

/* Form styling */
.manager-content .form-group {
    margin-bottom: 15px;
}
.manager-content .form-control {
    width: 100%;
}
</style>
