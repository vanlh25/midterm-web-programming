package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.User;

public interface UserService {

    // ---------------- CRUD ----------------
    User save(User user);
    List<User> findAll();
    User findById(int id);
    void deleteById(int id);
    User findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean updatePasswordByEmail(String email, String newPassword);

    // ---------------- Login và Register ----------------
    boolean register(String email, String fullname, int phone, String passwd);  
    User login(String email, String password);
    
    // ---------------- Phân trang ----------------	
    List<User> findAll(int page, int pageSize);
    long count();

}
