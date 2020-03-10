package com.library.dao;

import com.library.pojo.Book;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDao {
    private final static String NAMESPACE = "com.library.dao.BookDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    //使用模糊查询进行书籍查询
    public int matchBook(final String searchWord){
        String search="%"+searchWord+"%";
        return  sqlSessionTemplate.selectOne(NAMESPACE+"matchBook",search);
    }
//返回查询书本匹配列表
    public ArrayList<Book> queryBook(final String searchWord){
        String search="%"+searchWord+"%";
        List<Book> result =sqlSessionTemplate.selectList(NAMESPACE+"queryBook",search);
        return (ArrayList<Book>)result;
    }
    //获取所有书本
    public ArrayList<Book> getAllBooks() {
        List<Book> result = sqlSessionTemplate.selectList(NAMESPACE + "getAllBooks");
        return (ArrayList<Book>) result;
    }
//添加书本
    public int addBook(final Book book){
        return sqlSessionTemplate.insert(NAMESPACE+"addBook",book);
    }
    //获取书本
    public Book getBook(final long bookId){
        return sqlSessionTemplate.selectOne(NAMESPACE+"getBook",bookId);
    }
    //编辑书本
    public int editBook(final Book book){
     return sqlSessionTemplate.update(NAMESPACE+"editBook",book);
    }
    //删除书本
    public int deleteBook(final long bookId){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteBook",bookId);

    }

}
