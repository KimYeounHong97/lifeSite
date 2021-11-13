package com.life.site.web.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.life.site.model.FileVo;


@Repository
@Mapper
public interface RegisterMapper {
	public int insertAttachSave(FileVo file);
	public int insertTitleSave(FileVo file);
	public int changeTitleSave (Map<String, Object> param);
	public FileVo loadById(Long attach_id);
	public int insertAnimals (Map<String, Object> param);
	public int updateAnimals (Map<String, Object> param);
	public int updateEditorAttach (Map<String, Object> param);
}
