<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<div class="user-content mt-5">
    <h2>Chi tiết sách</h2>

    <!-- Nút quay lại danh sách -->
    <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-primary mb-3">← Quay lại</a>

    <div class="book-detail-card">
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
            <p><strong>Tiêu đề:</strong> ${book.title}</p>
            <p><strong>Mã ISBN:</strong> ${book.isbn}</p>
            <p><strong>Tác giả:</strong> ${authors}</p>
            <p><strong>Publisher:</strong> ${book.publisher}</p>
            <p><strong>Publish date:</strong> ${book.publishDate}</p>
            <p><strong>Quantity:</strong> ${book.quantity}</p>
            <p><strong>Reviews (${topReviews.size()}):</strong></p>

            <div class="reviews">
                <c:forEach var="r" items="${topReviews}">
                    <div class="review-item">
                        <strong>${r.user.fullname}:</strong> ${r.reviewText} 
                        <span class="rating">(${r.rating})</span>
                    </div>
                </c:forEach>
            </div>

            <!-- Form thêm review -->
            <c:if test="${not empty currentUser}">
                <form action="${pageContext.request.contextPath}/admin/book/detail" method="post">
                    <input type="hidden" name="id" value="${book.bookId}"/>
                    <div>
                        <label>Rating:</label>
                        <select name="rating" required>
                            <c:forEach var="i" begin="1" end="5">
                                <option value="${i}">${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label>Review:</label>
                        <textarea name="reviewText" rows="3" required></textarea>
                    </div>
                    <button type="submit">Submit</button>
                </form>
            </c:if>
        </div>
    </div>
</div>

<style>
.btn { 
    padding: 8px 15px; 
    border-radius: 4px; 
    text-decoration: none; 
}
.btn-primary { background-color: #007bff; color: #fff; }
.btn-primary:hover { background-color: #0069d9; }

.book-detail-card { display: flex; align-items: flex-start; background-color: #fff; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); border-radius: 6px; }
.book-cover { width: 150px; height: 220px; display: flex; align-items: center; justify-content: center; background-color: #f0f0f0; border-radius: 4px; flex-shrink: 0; text-align: center; padding: 5px; overflow: hidden; margin-right: 20px; }
.book-cover img { width: 100%; height: 100%; object-fit: cover; }
.book-cover .no-cover { font-size: 14px; font-weight: bold; color: #333; display: -webkit-box; -webkit-line-clamp: 4; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; }
.book-info { flex: 1; }
.book-info p { margin: 5px 0; }
.reviews { margin-top: 10px; padding: 10px; background-color: #fafafa; border-radius: 4px; }
.review-item { margin-bottom: 8px; }
.review-item .rating { color: #ff9800; font-weight: bold; }
form { margin-top: 15px; }
form textarea { width: 100%; resize: none; }
form select, form textarea { margin-bottom: 10px; padding: 5px; }
</style>
