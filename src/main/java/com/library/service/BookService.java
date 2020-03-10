package com.library.service;

import com.library.dao.BookDao;
import com.library.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;
    public ArrayList<Book> queryBook(String searchWord){
        return bookDao.queryBook(searchWord);
    }

    public ArrayList<Book> getAllBooks(){
        return bookDao.getAllBooks();
    }
    public boolean matchBook(String searchWord){
        return bookDao.matchBook(searchWord)>0;
    }
    public boolean addBook(Book book){
        return bookDao.addBook(book)>0;
    }
    public Book getBook(long bookId){
        return bookDao.getBook(bookId);
    }
    public boolean editBook(Book book){
        return bookDao.editBook(book)>0;
    }
    public boolean deleteBook(long bookId){
        return bookDao.deleteBook(bookId)>0;
    }

}
