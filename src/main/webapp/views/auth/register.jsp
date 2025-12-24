<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-8 col-md-offset-2 col-sm-12">
    <h1>Register</h1>
    <div class="content-form-page">
        <form action="${pageContext.request.contextPath}/auth/register" 
              method="post" class="form-horizontal form-without-legend" role="form">

            <!-- Email -->
            <div class="form-group">
                <label for="email" class="col-lg-3 control-label">Email <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="email" class="form-control" id="email" name="email" value="${param.email}" required>
                </div>
            </div>

            <!-- Full Name -->
            <div class="form-group">
                <label for="fullName" class="col-lg-3 control-label">Full Name <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="text" class="form-control" id="fullName" name="fullName" value="${param.fullName}" required>
                </div>
            </div>

            <!-- Phone -->
            <div class="form-group">
                <label for="phone" class="col-lg-3 control-label">Phone <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="text" class="form-control" id="phone" name="phone" value="${param.phone}" required>
                </div>
            </div>

            <!-- Password -->
            <div class="form-group">
                <label for="password" class="col-lg-3 control-label">Password <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
            </div>

            <!-- Hiển thị alert -->
            <c:if test="${not empty alert}">
                <div class="row">
                    <div class="col-lg-9 col-lg-offset-3 padding-top-10">
                        <div class="alert alert-danger">${alert}</div>
                    </div>
                </div>
            </c:if>

            <!-- Submit button -->
            <div class="row">
                <div class="col-lg-9 col-lg-offset-3 padding-top-20">
                    <button type="submit" class="btn btn-primary btn-lg">Register</button>
                </div>
            </div>

            <!-- Link login -->
            <div class="row">
                <div class="col-lg-9 col-lg-offset-3 padding-top-10">
                    <hr>
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
                </div>
            </div>

        </form>
    </div>
</div>
