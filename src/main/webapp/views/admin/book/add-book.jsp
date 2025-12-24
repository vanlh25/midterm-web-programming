<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="admin-content mt-5">
    <h2>Thêm sách mới</h2>

    <form action="${pageContext.request.contextPath}/admin/book/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" required/>
        </div>

        <div class="form-group">
            <label>Publisher:</label>
            <input type="text" name="publisher" class="form-control"/>
        </div>

        <div class="form-group">
            <label>Price:</label>
            <input type="number" step="0.01" name="price" class="form-control"/>
        </div>

        <div class="form-group">
            <label>Description:</label>
            <textarea name="description" class="form-control" rows="4"></textarea>
        </div>

        <div class="form-group">
            <label>Quantity:</label>
            <input type="number" name="quantity" class="form-control"/>
        </div>

        <div class="form-group">
            <label>Publish date:</label>
            <input type="date" name="publishDate" class="form-control"/>
        </div>

        <div class="form-group">
            <label>Cover image:</label>
            <div>
                <img id="coverPreview" src="" alt="Preview" style="width:100px;height:150px;object-fit:cover;margin-bottom:10px;display:none;"/>
            </div>
            <input type="file" name="coverImage" accept="image/*" class="form-control" onchange="previewImage(event)"/>
        </div>

        <div class="form-actions mt-3">
            <button type="submit" class="btn btn-success">Add Book</button>
            <a href="${pageContext.request.contextPath}/admin/book/list" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

<script>
function previewImage(event) {
    const input = event.target;
    const preview = document.getElementById('coverPreview');
    if(input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function() {
            preview.src = reader.result;
            preview.style.display = 'block';
        }
        reader.readAsDataURL(input.files[0]);
    }
}
</script>

<style>
.admin-content { background-color: #fff; padding: 20px; border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }
.form-group { margin-bottom: 15px; }
.form-group label { font-weight: bold; display: block; margin-bottom: 5px; }
.form-control { width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ccc; }
.form-actions { display: flex; gap: 10px; }
.btn { padding: 8px 15px; border-radius: 4px; text-decoration: none; border: none; cursor: pointer; }
.btn-success { background-color: #28a745; color: #fff; }
.btn-success:hover { background-color: #218838; }
.btn-secondary { background-color: #6c757d; color: #fff; text-align: center; line-height: 1.5; }
.btn-secondary:hover { background-color: #5a6268; }
</style>
