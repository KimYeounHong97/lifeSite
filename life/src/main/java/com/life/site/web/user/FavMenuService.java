package com.life.site.web.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.life.site.web.log.LoggerService;

@Service("FavMenuService")
public class FavMenuService {
    @Autowired
    FavMenuMapper mapper;

    @Autowired
    LoggerService loggerService;
    
    public List<Map<String, Object>> getFavoritesMenu(Map<String, Object> param) {
        return  mapper.selectFavoritesMenu(param);
    }

    public int addFavoritesMenu(Map<String, Object> param) {
        return mapper.insertFavoritesMenu(param);
    }

    public int delFavoritesMenu(Map<String, Object> param) {
        return mapper.deleteFavoritesMenu(param);
    }
}
