package com.library.dao;

import com.library.pojo.ReaderInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderInfoDao {
    private final static String NAMESPACE = "com.library.dao.ReaderInfoDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    //获取所有读者信息
    public ArrayList<ReaderInfo> getAllReaderInfo(){
        List<ReaderInfo> result=sqlSessionTemplate.selectList(NAMESPACE+"getAllReaderInfo");
        return (ArrayList<ReaderInfo>)result;
    }
    //通过读者id查询读者信息
    public ReaderInfo getReaderInfoByReaderId(final long reader_id){
        return sqlSessionTemplate.selectOne(NAMESPACE+"getReaderInfoByReaderId",reader_id);
    }
    //通过读者id删除读者信息
    public int deleteReaderInfo(final long reader_id){
        return sqlSessionTemplate.delete(NAMESPACE+"deleteReaderInfo",reader_id);
    }
    //编辑读者信息
    public int editReaderInfo(final ReaderInfo readerInfo){
        return sqlSessionTemplate.update(NAMESPACE+"editReaderInfo",readerInfo);

    }

    //更新读者卡的信息
    public int editReaderCard(final ReaderInfo readerInfo){
        return sqlSessionTemplate.update(NAMESPACE+"editReaderCard",readerInfo);
    }

//    添加读者信息

    /**
     *
     * @return  返回值就是自增的id
     */
    public final long addReaderInfo(final ReaderInfo readerInfo){
        if (sqlSessionTemplate.insert(NAMESPACE+"addReaderInfo",readerInfo)>0){
            //如果添加成功的话就返回自增的id
            return sqlSessionTemplate.selectOne(NAMESPACE+"getReaderId",readerInfo);
        }else {
            return -1;
        }
    }

}
