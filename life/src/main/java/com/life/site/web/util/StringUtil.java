package com.life.site.web.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

/**
 * @Date    : 2020. 3. 16.
 * @package : com.hansol.util
 * @file    : StringUtil.java
 * @Author  : PSJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 3. 16.      PSJ        최초 생성
 */
public class StringUtil {
    
    public static String getParameter(HttpServletRequest request, String field) {
        
        if (request instanceof StandardMultipartHttpServletRequest) {
            return request.getParameterValues(field)[0] == null ? "" : (String) request.getParameterValues(field)[0];
        }
        
        return request.getParameter(field) == null ? "" : (String) request.getParameter(field);
    }
    
    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static boolean isEmpty(String str) {
        if(str == null) return true;
        if("".equals(str)) return true;
        return false;
    }
    

    public static String nvl(String s)
    {
        return nvl(s, "");
    }

    public static String nvl(String s, String value)
    {
        return s != null && !s.trim().equals("null") && !s.trim().equals("") ? s : value;
    }

    public static String nvl(Object o)
    {
        return nvl(o, "");
    }

    public static String nvl(Object o, String value)
    {
        return o != null ? o.toString() : value;
    }
    
    /**
     * 좌측으로 값 채우기
     * @param strTarget     대상 문자열
     * @param strDest       채워질 문자열
     * @param nSize         총 문자열 길이
     * @return 채워진 문자열
     */
    public static String lpad(String strTarget, String strDest, int nSize){
        return padValue( strTarget, strDest, nSize, true );
    }

    /**
     * 좌측에 공백 채우기
     * @param strTarget     대상 문자열
     * @param nSize         총 문자열 길이
     * @return 채워진 문자열 길이
     */
    public static String lpad(String strTarget, int nSize){
        return padValue( strTarget, " ", nSize, true );
    }
    
    
    /**
     * 우측으로 값 채우기
     * @param strTarget     대상문자열
     * @param strDest       채워질 문자열
     * @param nSize         총 문자열 길이
     * @return 채워진 문자열 길이
     */
    public static String rpad(String strTarget, String strDest, int nSize){
        return padValue( strTarget, strDest, nSize, false );
    }

    /**
     *우측으로 공백 채우기
     * @param strTarget     대상문자열
     * @param nSize         총 문자열 길이
     * @return 채워진 문자열
     */
    public static String rpad(String strTarget, int nSize){
        return padValue( strTarget, " ", nSize, false );
    }
    
    /**
     * 값 채우기
     * @param strTarget     대상 문자열
     * @param strDest       채워질 문자열
     * @param nSize         총 문자열 길이
     * @param bLeft         채워질 문자의 방향이 좌측인지 여부
     * @return 지정한 길이만큼 채워진 문자열
     */
    public static String padValue(String strTarget, String strDest, int nSize, boolean bLeft){
        if ( strTarget == null )
            return strTarget;
        
        String retValue = null;
        
        StringBuffer objSB = new StringBuffer();
        
        int nLen = strTarget.length();
        int nDiffLen = nSize - nLen;
        //System.out.println();
        for (int i = 0 ; i < nDiffLen ; i++){
            objSB.append( strDest );
        }
        
        if ( bLeft == true ) // 채워질 문자열의 방향이 좌측일 경우
            retValue = objSB.toString() + strTarget;
        else
            // 채워질 문자열의 방향이 우측일 경우
            retValue = strTarget + objSB.toString();
        
        return retValue;
    }
    
    public static String ksctoUni(String str) throws Exception{
        return new String(str.getBytes("KSC5601"),"8859_1");
    }
    
}
