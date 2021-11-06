package com.life.site.web.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface PostMapper {
    public List<HashMap<String, Object>> selectPostAnimalList(Map<String, Object> param);
    public HashMap<String, Object> selectPostAnimalInfo(Map<String, Object> param);
    public List<Map<String, Object>> selectPostAttachList(Map<String, Object> param);
	public int insertAnimals(Map<String, Object> param);
	public int deleteAnimals (Map<String, Object> param);
}
