package com.library.dao;

import com.library.pojo.Lend;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LendDao {
    private final static String NAMESPACE = "com.library.dao.LendDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;


    //更新个人归还的书本记录
    public int returnBookOne(final long book_id,long reader_id){
        Map<String,Object> map=new HashMap<>();
        map.put("book_id",book_id);
        map.put("reader_id",reader_id);
        return sqlSessionTemplate.update(NAMESPACE+"returnBookOne",map);
    }
    //更新归还记录
    public int returnBookTwo(final long book_id)
    {
        return sqlSessionTemplate.update(NAMESPACE+"returnBookTwo",book_id);
    }
    //更新个人借出书本的记录
    public int lendBookOne(final long book_id,final long reader_id){
        Map<String,Object> map=new HashMap<>();
        map.put("book_id",book_id);
        map.put("reader_id",reader_id);
        return sqlSessionTemplate.insert(NAMESPACE+"lendBookOne",map);
    }

    //更新借出记录
    public int lendBookTwo(final long book_id){
        return sqlSessionTemplate.update(NAMESPACE+"lendBookTwo",book_id);
    }
    //返回全部借书记录
    public ArrayList<Lend> lendList() {
        List<Lend> result = sqlSessionTemplate.selectList(NAMESPACE + "lendList");
        return (ArrayList<Lend>) result;
    }


    //返回个人借书记录
    public ArrayList<Lend> myLendList(final long reader_id){
        List<Lend> result=sqlSessionTemplate.selectList(NAMESPACE+"myLendList",reader_id);
        return (ArrayList<Lend>)result;
    }

    //删除借书记录
    public int deleteLend(final long ser_num){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteLend",ser_num);
    }


}
