package com.life.site.config.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriTemplate;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.util.IpUtil;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;


public class CommonInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    LoggerService loggerService;
    
    protected Log log = LogFactory.getLog(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            String url = request.getRequestURI();
            String ip = IpUtil.getClientIpAddr(request);
            UserVo member = SessionManager.getUser(request);

            String returnMsg = "";

            log.debug("====================================== START ======================================");
            log.debug(" ======> Request URL\t: " + url);
            log.debug(" ======> FROM IP\t: " + ip);

            do {
                // 세션이 없을 때
                if (member == null) {
                    // AJAX호출은 에러코드로 리턴하고 페이지 호출은 login 페이지로 redirect 한다.
                    log.debug(" ======> session member 만료!!!");
                    returnMsg = "로그인 시간이 만료되었습니다. 다시 로그인해 주세요.";
                    break;
                }
                // 세션 OK
                else {
                    // PARAMETER BODY
                    StringBuilder parameterMessage = new StringBuilder();
                    Map<String, String[]> parameters = request.getParameterMap();
                    for (Object key : parameters.keySet()) {
                        String keyStr = (String) key;
                        String[] value = (String[]) parameters.get(keyStr);
                        parameterMessage.append((String) key + " : " + Arrays.toString(value) + " ");
                    }
                    
                    // 화면VIEW - PROGRAM_CD url에서 추출, 20201224
                    String programCd = "";
                    if(url.indexOf("/view/page") > -1 ) {
                        UriTemplate template = new UriTemplate("view/page/{prgm_cd}");
                        Map<String, String> urlMap = new HashMap<>();
                        urlMap = template.match(url);
                        if(!urlMap.isEmpty()) {
                            programCd = urlMap.get("prgm_cd");
                        }
//                        System.out.println("==== urlMap : " + urlMap.size());
//                        System.out.println("==== urlMap : " + urlMap.toString());
//                        System.out.println("==== urlMap : " + urlMap.get("prgm_cd"));
                    }

                    log.debug(" ======> 세션 USER\t: " + member.getUSER_ID() + " / " + member.getUSER_NM());
                    String sessionCreateIp = SessionManager.getUserIp(request);

                    // Session 생성 IP가 없으면 안된다.
                    if ("".equals(sessionCreateIp)) {
                        // AJAX호출은 에러코드로 리턴하고 페이지 호출은 login 페이지로 redirect 한다.
                        log.debug(" ======> 세션IP is null");
                        returnMsg = "접속 정보가 부족합니다. 다시 로그인해 주세요.";
                        break;
                    }
                    // 화면(프로그램) 접근 권한 체크
                    else if(url.indexOf("/view/page") > -1
                            && loggerService.checkAuthByPrgmcd(member, programCd) < 1) {
                        log.debug(" ======> 화면 접근권한 없음. PROGRAM_CD = " + programCd);
                        returnMsg = "해당 화면의 접근권한이 없습니다. 다시 로그인해 주시거나 관리자에게 문의해 주세요.";
                        break;
                    }
                    else {
                        // 화면 오픈 시 session에 담겨져 있던 user_id 체크
                        String loginUserId = StringUtil.getParameter(request, CommonConstants.Params.LOGIN_USERID);
                        
                        log.debug(" ======> Params\t: " + parameterMessage);

                        if ("".equals(loginUserId)) { //null이면 body에서 찾아본다.
                            String body = getHttpBody(request);
                            body = body.trim();
                            log.debug(" ======> BODY INFO\t: " + body.length());
                            
                            JSONParser jsonParser = new JSONParser();
                            if (body !=null && body.length() > 5) {
                                JSONObject bodyObject = (JSONObject) jsonParser.parse(body);  
                                if (!bodyObject.isEmpty() && bodyObject.get(CommonConstants.Params.LOGIN_USERID) !=null) {
                                    loginUserId =  bodyObject.get(CommonConstants.Params.LOGIN_USERID).toString();
                                }
                            }
                        }
                        
                        // 현재 session의 user_id와 client에서 넘겨준 session_user_id 값 비교 후 다르면 reject (새창으로 열기 시 권한 체크 issue) => AJAX CALL (/api)만 체크
                        //log.debug("loginUserId " + loginUserId);
                        if (!loginUserId.equals(member.getUSER_ID()) && (url.indexOf("/api") > -1)) {
                            log.debug(" ======> loginUserId => " + loginUserId + " |||| session userid => " + member.getUSER_ID());
                            log.debug(" ======> 로그인한 사용자(session)와 화면의 사용자가 서로 다릅니다.");
                            returnMsg = "세션 정보가 상이합니다. 다시 로그인해 주세요.";
                            break;
                        }

                        return true;
                    }
                }
            } while (false);

            // false일 경우
            // AJAX호출은 에러코드로 리턴하고 페이지 호출은 login 페이지로 redirect 한다.
            if (url.indexOf("/api") > -1) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, returnMsg);
            } else {
                // parameter 노출을 없애기 위해 forward (POST) 사용
                RequestDispatcher dispatcher = request.getRequestDispatcher("/timeout?message=" + returnMsg);
                dispatcher.forward(request, response);
            }

            log.debug("====================================== END ======================================\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        log.debug("====================================== END ======================================\n");
    }

    public static String getHttpBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}