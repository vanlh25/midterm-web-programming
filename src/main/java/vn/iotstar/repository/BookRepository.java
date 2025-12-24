package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.Book;

public interface BookRepository {

    // Create / Update
    Book save(Book book);

    // Read
    List<Book> findAll();
    Book findById(int id);

    // Delete
    void deleteById(int id);
    
    List<Book> findAll(int page, int pageSize);
    long count();

}