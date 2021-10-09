package com.life.site.config.interceptor;

import com.life.site.config.annotation.UserSession;
import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;
import com.life.site.web.util.session.SessionLife;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;
    
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //@UserSession 어노테이션이 들어있으면
        boolean isLoginUserAnnotation = methodParameter.getParameterAnnotation(UserSession.class) != null;
        //SessionUser 클래스 타입의 파라미터에 @UserSession 어노테이션이 사용되었는가?
        boolean isUserClass = UserVo.class.equals(methodParameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        return ((SessionLife)httpSession.getAttribute(CommonConstants.HANSOL_SESSION)).getUser();
    }
}
