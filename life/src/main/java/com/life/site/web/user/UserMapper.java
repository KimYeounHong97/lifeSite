package com.life.site.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Date    : 2021. 1. 14.
 * @package : com.hansol.user
 * @file    : UserMapper.java
 * @Author  : sujin
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2021.1.14.   sujin        최초 생성
 */
@Repository
@Mapper
public interface UserMapper {
    public List<HashMap<String, Object>> selectUserList(Map<String, Object> param);
    public List<HashMap<String, Object>> getAllOrgList(Map<String, Object> param);
    public List<HashMap<String, Object>> getUserOrgList(Map<String, Object> param);
    public List<HashMap<String, Object>> getDeptList(Map<String, Object> param);
}
