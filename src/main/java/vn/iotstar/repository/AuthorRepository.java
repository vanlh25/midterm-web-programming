package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.Author;

public interface AuthorRepository {

    // Create / Update
    Author save(Author author);

    // Read
    List<Author> findAll();
    Author findById(int id);

    // Delete
    void deleteById(int id);
    
    List<Author> findAll(int page, int pageSize);   // phân trang
    long count();                                   // tổng số tác giả

}
