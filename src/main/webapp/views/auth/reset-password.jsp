<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-8 col-md-offset-2 col-sm-12">
    <h1>Reset Password</h1>
    <div class="content-form-page">
        <form action="${pageContext.request.contextPath}/auth/reset-password" 
              method="post" class="form-horizontal form-without-legend" role="form">

            <!-- New Password -->
            <div class="form-group">
                <label for="password" class="col-lg-3 control-label">New Password <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
            </div>

            <!-- Confirm Password -->
            <div class="form-group">
                <label for="confirmPassword" class="col-lg-3 control-label">Confirm Password <span class="require">*</span></label>
                <div class="col-lg-9">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
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
                    <button type="submit" class="btn btn-primary btn-lg">Reset Password</button>
                </div>
            </div>

            <!-- Link login -->
            <div class="row">
                <div class="col-lg-9 col-lg-offset-3 padding-top-10">
                    <hr>
                    <p>Remember your password? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
                </div>
            </div>

        </form>
    </div>
</div>
