package com.life.site.config.logging.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua_parser.Client;
import ua_parser.Parser;

public class AgentUtils {
    private final static String UNKNOWN = "UNKNOWN";
    public  static String getUserAgentString(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public  static String getUserAgentString() {
		return getUserAgentString(HttpUtils.getCurrentRequest());
	}
	
	public  static String getBrowser(HttpServletRequest request) {
        Client c = getClient(request);
        return c == null ? UNKNOWN : c.userAgent.family;
    }
	

    public  static String getBrowser() {
        return getBrowser(HttpUtils.getCurrentRequest());
    }
    
	public static Client getClient(HttpServletRequest request) {
	    try {
			String userAgentString = getUserAgentString(request);
		    return new Parser().parse(userAgentString);
		} catch (Exception e) {
			// ignored
		}
		return null;
	}

	public static Client getClient() {
		return getClient(HttpUtils.getCurrentRequest());
	}

	public static String getUserOs(HttpServletRequest request) {
		Client c = getClient(request);
		return c.userAgent == null ? UNKNOWN : c.os.family;
	}

	public static String getUserOs() {
		return getUserOs(HttpUtils.getCurrentRequest());
	}

	public static String getBrowserVersion(HttpServletRequest request) {
		 Client c = getClient(request);
		return c == null ? UNKNOWN : c.userAgent.major + "."+ c.userAgent.minor;
	}

	public static  String getBrowserVersion() {
		return getBrowserVersion(HttpUtils.getCurrentRequest());
	}

	public static String getDeviceType(HttpServletRequest request) {
		Client c = getClient(request);
		return c == null ? UNKNOWN : c.device.family;
	}

	public static String getDeviceType() {
		return getDeviceType(HttpUtils.getCurrentRequest());
	}
	
	public static Map<String, String> getAgentDetail(HttpServletRequest request) {
		Map<String, String> agentDetail = new HashMap<>();
		agentDetail.put("browser", getBrowser(request).toString());
		agentDetail.put("browserVersion", getBrowserVersion(request).toString());
		agentDetail.put("os", getUserOs(request).toString());
		agentDetail.put("deviceType", getDeviceType(request).toString());
		return agentDetail;
	}
}
