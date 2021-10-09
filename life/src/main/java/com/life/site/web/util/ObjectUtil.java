package com.life.site.web.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date    : 2020. 7. 22.
 * @package : com.hansol.util
 * @file    : ObjectUtil.java
 * @Author  : PSJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 7. 22.      PSJ        최초 생성
 *
 */
public class ObjectUtil {

    public static HashMap<String, Object> getHashMapParameter(HttpServletRequest request, boolean URLDecode) throws Exception {
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
//            String returl = request.getServletPath();
            Enumeration<String> keys = request.getParameterNames();
//          for (int i = 0; keys.hasMoreElements(); i++) {
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String[] aVal = request.getParameterValues(key);
//                returl += (i == 0) ? "?" : "&";
                for (int j = 0; j < aVal.length; j++) {
                    if (URLDecode)
                        map.put(key, URLDecoder.decode(aVal[j], "UTF-8"));
                    else
                        map.put(key, aVal[j]);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 리스트 트리로 변환
     */
    public static List<Map<String, Object>> addTreeObject(List<Map<String, Object>> treeList) {
        List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        int i = 0;

        while (treeList.size() != i) {
            i = addChildren(treeList, jsonList, i);
        }
        return jsonList;
    }

    /*    *//**
             * 트리구조 객체 재귀함수
             *//*
                * @SuppressWarnings("unchecked") public static int
                * addChildren2(List<Map<String, Object>> ordList, @SuppressWarnings("rawtypes")
                * List newList, int idx){ CommonTreeVo treeVo = (CommonTreeVo)ordList.get(idx);
                * //VO -> HashMap으로 변경 Rediso는 Map이랑 DTO 섞이면 모르는듯함
                * 
                * idx = idx + 1;
                * 
                * int cnt = treeVo.sub; if(cnt > 0){ List<Map<String, Object>> subList = new
                * ArrayList<Map<String, Object>>();
                * 
                * while(cnt != subList.size()){ idx = addChildren(ordList, subList, idx); }
                * 
                * treeVo.setChildren(subList); } newList.add(treeVo);
                * 
                * return idx; }
                */

    /**
     * 트리구조 객체 재귀함수
     */
    @SuppressWarnings("unchecked")
    public static int addChildren(List<Map<String, Object>> ordList, @SuppressWarnings("rawtypes") List newList,
            int idx) {

        Map<String, Object> treeVo = (Map<String, Object>) ordList.get(idx);
        // VO -> HashMap으로 변경 Rediso는 Map이랑 DTO 섞이면 모르는듯함

        idx = idx + 1;

        int cnt = 0;
        if(treeVo.get("SUB")!=null) {

            String str_sub_cnt = String.valueOf(treeVo.get("SUB"));
            cnt = Integer.parseInt(str_sub_cnt);
        }
        if (cnt > 0) {
            List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();

            while (cnt != subList.size()) {
                idx = addChildren(ordList, subList, idx);
            }
            treeVo.put("CHILDREN", subList);
        } else {
            treeVo.put("CHILDREN", new ArrayList<Map<String, Object>>());
        }
        newList.add(treeVo);

        return idx;
    }
    
}
