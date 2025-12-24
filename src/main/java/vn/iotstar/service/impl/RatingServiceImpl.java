package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.entity.Rating;
import vn.iotstar.repository.RatingRepository;
import vn.iotstar.service.RatingService;

public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(int userId, int bookId) {
        return ratingRepository.findById(userId, bookId);
    }

    @Override
    public boolean deleteById(int userId, int bookId) {
        Rating rating = ratingRepository.findById(userId, bookId);

        if (rating == null) {
            return false;
        }

        ratingRepository.deleteById(userId, bookId);
        return true;
    }
    
    @Override
    public List<Rating> findAll(int page, int pageSize) {
        return ratingRepository.findAll(page, pageSize);
    }

    @Override
    public long count() {
        return ratingRepository.count();
    }
}
