package com.library.service;

import com.library.dao.AdminDao;
import com.library.dao.ReaderCardDao;
import com.library.pojo.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private ReaderCardDao readerCardDao;
    //管理员登录验证
    public boolean hasMatchAdmin(long adminId,String password ){
    return adminDao.getMatchCount(adminId,password)==1;
    }

    //管理员重置密码
    public boolean adminRestPassword(long adminId,String password){
        return adminDao.resetPassword(adminId,password)>0;
    }
    //获取管理员密码密码
    public String getAdminPassword(long adminId){
        return adminDao.getPassword(adminId);
    }
    //获取管理员姓名
    public String getAdminUsername(long adminId){
        return adminDao.getUsername(adminId);
    }

    //判断读者登录信息匹配
    public boolean hasMatchReader(long readerId,String password ){
        return readerCardDao.getIdMatchCount(readerId,password)>0;
    }
    //通过id进行寻找读者
    public ReaderCard findReaderCardById(long readerId){
        return readerCardDao.findReaderByReaderId(readerId);
    }
    //获取读者密码
    public String getReaderPassword(long readerId){
        return readerCardDao.getPassword(readerId);
    }
    //读者密码重置
    public boolean readerRePassword(long readerId,String newPassword){
        return readerCardDao.resetPassword(readerId,newPassword)>0;
    }
}
