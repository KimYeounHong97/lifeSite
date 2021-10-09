package com.life.site.web.util;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Date : 2020. 7. 22.
 * @package : com.hansol.util
 * @file : PnsUtil.java
 * @Author : PSJ
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
public class PnsUtil {
    
    public static String getLang() {
        return LocaleContextHolder.getLocale().toString();
    }
}
