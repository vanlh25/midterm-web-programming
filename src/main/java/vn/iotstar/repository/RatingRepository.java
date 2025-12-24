package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.Rating;

public interface RatingRepository {

    // Create / Update
    Rating save(Rating rating);

    // Read
    List<Rating> findAll();
    Rating findById(int userId, int bookId);  // composite key

    // Delete
    void deleteById(int userId, int bookId);
    
    List<Rating> findAll(int page, int pageSize);  // phân trang
    long count();                                   // tổng số bản ghi
}
