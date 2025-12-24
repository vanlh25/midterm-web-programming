package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Rating;

public interface RatingService {

    Rating save(Rating rating);

    List<Rating> findAll();

    Rating findById(int userId, int bookId);

    boolean deleteById(int userId, int bookId);
    
    List<Rating> findAll(int page, int size);
    long count();
}
