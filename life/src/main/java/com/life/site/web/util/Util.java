package com.life.site.web.util;

import org.springframework.context.i18n.LocaleContextHolder;

public class Util {
    
    public static String getLang() {
        return LocaleContextHolder.getLocale().toString();
    }
}
