package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Book;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();

    Book findById(int id);

    boolean deleteById(int id);
    
    List<Book> findAll(int page, int pageSize);
    long count();

}
