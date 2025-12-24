package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.entity.Book;
import vn.iotstar.repository.BookRepository;
import vn.iotstar.service.BookService;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Book book = bookRepository.findById(id);

        if (book == null) {
            return false;
        }

        bookRepository.deleteById(id);
        return true;
    }
    
    @Override
    public List<Book> findAll(int page, int pageSize) {
        return bookRepository.findAll(page, pageSize);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

}
