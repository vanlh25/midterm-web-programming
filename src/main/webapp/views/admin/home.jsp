<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="user-taskbar mb-4">
    <!-- Home luôn active -->
    <a href="${pageContext.request.contextPath}/admin/home"
       class="btn btn-primary active">Home</a>
    <!-- Profile -->
    <a href="${pageContext.request.contextPath}/admin/profile"
       class="btn btn-secondary">Profile</a>
</div>

<div class="user-content mt-5 text-center">
    <h1 style="font-size:60px; margin-top:150px;">
        Chào mừng <strong>${fullname}</strong>!
    </h1>
</div>

<style>
.user-taskbar .btn {
    margin-right: 10px;
    transition: all 0.2s ease;
    font-size: 16px;
    padding: 10px 20px;
}
.user-taskbar .btn.active {
    transform: translateY(-2px);
    box-shadow: 0 3px 8px rgba(0,0,0,0.25);
}
.user-taskbar .btn:hover {
    opacity: 0.9;
}
.user-content h1 {
    color: #333;
}
</style>
