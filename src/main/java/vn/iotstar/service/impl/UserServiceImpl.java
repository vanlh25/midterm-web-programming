package vn.iotstar.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.repository.impl.UserRepositoryImpl;
import vn.iotstar.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    // Inject repository
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ======================= CRUD =======================

    public UserServiceImpl() {
    	this.userRepository = new UserRepositoryImpl();
	}

	@Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean updatePasswordByEmail(String email, String newPassword) {
        return userRepository.updatePasswordByEmail(email, newPassword);
    }

    // Đăng ký tài khoản
    @Override
    public boolean register(String email, String fullname, int phone, String passwd) {

        // Email đã tồn tại --> thất bại
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setPasswd(passwd);
        user.setSignupDate(LocalDateTime.now());
        user.setLastLogin(null);
        user.setIsAdmin(false); // mặc định không phải admin

        userRepository.save(user);
        return true;
    }

    // Đăng nhập
    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPasswd().equals(password)) {
            return null;
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }
    
    // Phân trang
    @Override
    public List<User> findAll(int page, int pageSize) {
        return userRepository.findAll(page, pageSize);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
