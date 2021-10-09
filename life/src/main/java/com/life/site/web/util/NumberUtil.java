package com.life.site.web.util;

import java.text.DecimalFormat;

/**
 * @Date    : 2020. 7. 22.
 * @package : com.hansol.util
 * @file    : NumberUtil.java
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
public class NumberUtil {

    /**
     * 숫자 (금액)에 콤마(,) 추가
     * 
     * @param number data (String)
     * @return
     */
    public static String getNumberComma(String num) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(Double.parseDouble(num));
    }
}
