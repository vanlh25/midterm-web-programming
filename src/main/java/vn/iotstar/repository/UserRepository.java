package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.User;

public interface UserRepository {

    // ---------------- CRUD cơ bản ----------------
    
    User save(User user);               // Thêm mới hoặc cập nhật
    List<User> findAll();               // Lấy tất cả user
    User findById(int id);              // Tìm theo id
    void deleteById(int id);            // Xóa theo id
    User findByEmail(String email); 	  // Tìm theo email
    
    // ---------------- Các hàm bổ sung ----------------
    
    boolean existsByEmail(String email);        // Kiểm tra email tồn tại
    boolean updatePasswordByEmail(String email, String newPassword); // Cập nhật mật khẩu theo email

    List<User> findAll(int page, int pageSize);   // phân trang
    long count();                                 // tổng số dòng
}
