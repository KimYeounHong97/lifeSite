package com.life.site.web.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Package  : com.hansol.user.FavMenuMapper
 * @Authr    : hana.k
 * @Date     : 2020. 09. 17.
 * @Desc     : 
 */
@Repository
@Mapper
public interface FavMenuMapper {
    public List<Map<String, Object>> selectFavoritesMenu(Map<String, Object> param);
    public int insertFavoritesMenu(Map<String, Object> param);
    public int deleteFavoritesMenu(Map<String, Object> param);
}
