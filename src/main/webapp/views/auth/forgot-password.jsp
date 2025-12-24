<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="jakarta.tags.core"%>

<div class="col-md-9 col-sm-9 col-md-offset-1"> <!-- Có thể căn giữa -->
    <h1>Forgot Password</h1>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-12 col-sm-12"> <!-- Chiếm hết chiều rộng -->

                <form action="${pageContext.request.contextPath}/auth/forgot-password" 
                      method="post" class="form-horizontal form-without-legend" role="form">

                    <div class="form-group">
                        <label for="email" class="col-lg-3 control-label">Email <span class="require">*</span></label>
                        <div class="col-lg-9">
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                    </div>

                    <c:if test="${not empty alert}">
                        <div class="row">
                            <div class="col-lg-9 col-lg-offset-3 padding-top-10">
                                <div class="alert alert-danger">${alert}</div>
                            </div>
                        </div>
                    </c:if>

                    <div class="row">
                        <div class="col-lg-9 col-lg-offset-3 padding-left-0 padding-top-20">
                            <button type="submit" class="btn btn-primary btn-lg">Submit</button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-9 col-lg-offset-3 padding-left-0 padding-top-10 padding-right-30">
                            <hr>
                            <p>Remember your password? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
                        </div>
                    </div>

                </form>

            </div>
        </div>
    </div>
</div>
