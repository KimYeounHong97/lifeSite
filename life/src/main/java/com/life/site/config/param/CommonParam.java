package com.life.site.config.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.ws.transport.support.EnumerationIterator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonParam  implements Serializable {
    
    private static final long serialVersionUID = 130232796166547927L;

    private String loginUserId;
    private String data;
    
    @SuppressWarnings("unchecked")
    public Map<String,Object> getData() throws JsonMappingException, JsonProcessingException{
        Map<String,Object> result = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        
        if(this.data != null && !this.data.isEmpty() ) {
            result = (Map<String,Object>) mapper.readValue(this.data, Map.class);
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String,Object> getMultiData(MultipartHttpServletRequest request) throws JsonMappingException, JsonProcessingException{
        Map<String,Object> data = new HashMap<String,Object>();
        Iterator<String> param = new EnumerationIterator(request.getParameterNames());
        
        while(param.hasNext()) {
            String value = param.next();
            data.put(value,request.getParameter(value));
        }
        return data;
    }
    
    public List<Map<String, Object>> getDataList() throws JsonMappingException, JsonProcessingException{
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        ObjectMapper mapper = new ObjectMapper();
        
        if(this.data != null && !this.data.isEmpty() ) {
            result = mapper.readValue(this.data, new TypeReference<List<Map<String, Object>>>(){});
        }
        return result;
    }
}