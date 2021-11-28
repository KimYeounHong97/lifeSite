package com.life.site.web.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface PostMapper {
	public List<HashMap<String, Object>> selectPickPostList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostAnimalList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostArtEntertainmentList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostDestinationsList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostHistoryList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostLifeStyleList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostNatureList(Map<String, Object> param);
    public List<HashMap<String, Object>> selectPostPeopleList(Map<String, Object> param);
    
    public HashMap<String, Object> selectPostAnimalInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostArtEntertainmentInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostDestinationsInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostHistoryInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostLifeStyleInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostNatureInfo(Map<String, Object> param);
    public HashMap<String, Object> selectPostPeopleInfo(Map<String, Object> param);
    
    public List<Map<String, Object>> selectPostAttachList(Map<String, Object> param);
    
    public int  deletePostAttachList(Map<String, Object> param);
	public int deleteAnimals (Map<String, Object> param);
	public int deleteArtEntertainment (Map<String, Object> param);
	public int deleteDestinations (Map<String, Object> param);
	public int deleteHistory (Map<String, Object> param);
	public int deleteLifeStyle (Map<String, Object> param);
	public int deleteNature (Map<String, Object> param);
	public int deletePeople (Map<String, Object> param);
	
	public int updatePostPickAnimals (Map<String, Object> param);
	public int updatePostPickArtEntertainment (Map<String, Object> param);
	public int updatePostPickDestinations (Map<String, Object> param);
	public int updatePostPickHistory (Map<String, Object> param);
	public int updatePostPickLifeStyle (Map<String, Object> param);
	public int updatePostPickNature (Map<String, Object> param);
	public int updatePostPickPeople (Map<String, Object> param);
}
