<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="user-content mt-5">
    <h2>Danh sách sách</h2>

    <!-- Nút thêm sách -->
    <a href="${pageContext.request.contextPath}/admin/book/add" class="btn btn-success mb-3">+ Thêm sách mới</a>

    <c:forEach var="book" items="${books}">
        <div class="book-card">
            <div class="book-cover">
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/image?fname=${book.coverImage}" 
                             alt="${book.title}"/>
                    </c:when>
                    <c:otherwise>
                        <div class="no-cover">${book.title}</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="book-info">
                <h3>
                    <a href="${pageContext.request.contextPath}/admin/book/detail?id=${book.bookId}" 
                       class="book-title-link">${book.title}</a>
                </h3>
                <p><strong>Mã ISBN:</strong> ${book.isbn}</p>
                <p><strong>Tác giả:</strong> ${book.description}</p>
                <p><strong>Publisher:</strong> ${book.publisher}</p>
                <p><strong>Publish date:</strong> ${book.publishDate}</p>
                <p><strong>Quantity:</strong> ${book.quantity}</p>

                <!-- Các nút hành động -->
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/admin/book/detail?id=${book.bookId}" 
                       class="btn btn-primary">Chi tiết</a>
                    <a href="${pageContext.request.contextPath}/admin/book/edit?id=${book.bookId}" 
                       class="btn btn-warning">Chỉnh sửa</a>
                    <a href="${pageContext.request.contextPath}/admin/book/delete?id=${book.bookId}" 
                       class="btn btn-danger"
                       onclick="return confirm('Bạn có chắc muốn xóa sách này không?');">Xóa</a>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty books}">
        <p>Không có sách nào!</p>
    </c:if>

    <!-- Phân trang -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center mt-4">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</div>

<style>
/* Card sách */
.book-card {
    display: flex;
    align-items: flex-start;
    background-color: #fff;
    padding: 15px;
    margin-bottom: 15px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    border-radius: 6px;
}

.book-cover {
    width: 100px;
    height: 140px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #f0f0f0;
    border-radius: 4px;
    flex-shrink: 0;
    text-align: center;
    padding: 5px;
    overflow: hidden;
}

.book-cover img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 4px;
}

.book-cover .no-cover {
    font-size: 12px;
    font-weight: bold;
    color: #333;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.book-info {
    flex: 1;
    margin-left: 15px;
}

.book-info h3 {
    margin: 0 0 5px 0;
}

.book-info p {
    margin: 3px 0;
}

/* Các nút hành động */
.action-buttons .btn {
    margin-right: 5px;
    margin-top: 5px;
}

/* Màu sắc các nút */
.btn-primary { background-color: #007bff; color: #fff; }
.btn-warning { background-color: #ffc107; color: #fff; }
.btn-danger { background-color: #dc3545; color: #fff; }
.btn-success { background-color: #28a745; color: #fff; }

.btn:hover { opacity: 0.9; }

/* Phân trang */
.pagination {
    display: flex;
    list-style: none;
    padding-left: 0;
}

.pagination .page-item { margin: 0 3px; }

.pagination .page-link {
    display: block;
    padding: 5px 10px;
    color: #007bff;
    text-decoration: none;
    border: 1px solid #dee2e6;
    border-radius: 4px;
}

.pagination .page-item.disabled .page-link { pointer-events: none; opacity: 0.5; }
.pagination .page-item.active .page-link { background-color: #007bff; border-color: #007bff; color: #fff; }
</style>
