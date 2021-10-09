package com.life.site.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    public static MessageSource messageSource;

    @Autowired
    private MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }
    
    /**
     * 현재 Locale 설정에 맞는 message.properties를 참고하여 문자열을 리턴한다.
     * 
     * @param key   message_XX.properties에서 사용할 key 값
     * @param args  arguments
     * @return      message_XX.properties에 정의된 value String
     */
    public static String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
