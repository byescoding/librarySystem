package com.library.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//属于component注解下 可以吧bean自动注入
@Repository
public class AdminDao {
    private final static String NAMEAPACE="com.library.dao.AdminDao.";
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    //获取账户匹配信息 返回一个整数表示匹配信息条数selectOne表示只返回一条数据多于一条数据就会报错
    public int getMatchCount(final long admin_id,final String password){
        Map<String ,Object> parmMap=new HashMap<>();
        parmMap.put("admin_id",admin_id);
        parmMap.put("password",password);
        return sqlSessionTemplate.selectOne(NAMEAPACE+"getMatchCount",parmMap);

    }
    //重置密码 返回一个整数表示修改成功
    public  int resetPassword(final long admin_id,final String password){
   Map<String ,Object> paramMap=new HashMap<>();
   paramMap.put("admin_id",admin_id);
   paramMap.put("password",password);
   return sqlSessionTemplate.update(NAMEAPACE+"resetPassword",paramMap);
    }
    //获取密码
    public String getPassword(final long admin_id){
        return sqlSessionTemplate.selectOne(NAMEAPACE+"getPassword",admin_id);
    }

    //获取用户名
    public String getUsername(final long admin_id){
        return sqlSessionTemplate.selectOne(NAMEAPACE+"getUsername",admin_id);
    }
}
