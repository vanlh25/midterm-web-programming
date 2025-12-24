package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.entity.Author;
import vn.iotstar.repository.AuthorRepository;
import vn.iotstar.service.AuthorService;

public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(int id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Author a = authorRepository.findById(id);
        if (a == null) {
            return false;
        }

        authorRepository.deleteById(id);
        return true;
    }
    
    @Override
    public List<Author> findAll(int page, int pageSize) {
        return authorRepository.findAll(page, pageSize);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

}
