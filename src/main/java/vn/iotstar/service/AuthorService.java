package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Author;

public interface AuthorService {

    Author save(Author author);

    List<Author> findAll();

    Author findById(int id);

    boolean deleteById(int id);
    
    List<Author> findAll(int page, int pageSize);
    long count();

}
