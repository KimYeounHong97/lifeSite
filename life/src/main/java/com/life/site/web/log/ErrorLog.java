package com.life.site.web.log;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorLog {
    private String id;

    private String phase;


    private String system;


    private String loggerName;


    private String serverName;


    private String hostName;


    private String path;


    private String message;


    private String trace;

    @Builder.Default
    private LocalDateTime errorDatetime = LocalDateTime.now();

    @Builder.Default
    private String alertYn = "N";


    private String headerMap;


    private String parameterMap;


    private String userInfo;

    private String agentDetail;

  

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorLog errorLog = (ErrorLog) o;

        if (id != null ? !id.equals(errorLog.id) : errorLog.id != null) return false;
        if (phase != null ? !phase.equals(errorLog.phase) : errorLog.phase != null) return false;
        if (system != null ? !system.equals(errorLog.system) : errorLog.system != null) return false;
        if (loggerName != null ? !loggerName.equals(errorLog.loggerName) : errorLog.loggerName != null) return false;
        if (serverName != null ? !serverName.equals(errorLog.serverName) : errorLog.serverName != null) return false;
        if (hostName != null ? !hostName.equals(errorLog.hostName) : errorLog.hostName != null) return false;
        if (path != null ? !path.equals(errorLog.path) : errorLog.path != null) return false;
        if (message != null ? !message.equals(errorLog.message) : errorLog.message != null) return false;
        if (trace != null ? !trace.equals(errorLog.trace) : errorLog.trace != null) return false;
        if (errorDatetime != null ? !errorDatetime.equals(errorLog.errorDatetime) : errorLog.errorDatetime != null)
            return false;
        if (alertYn != null ? !alertYn.equals(errorLog.alertYn) : errorLog.alertYn != null) return false;
        if (headerMap != null ? !headerMap.equals(errorLog.headerMap) : errorLog.headerMap != null) return false;
        if (parameterMap != null ? !parameterMap.equals(errorLog.parameterMap) : errorLog.parameterMap != null)
            return false;
        if (userInfo != null ? !userInfo.equals(errorLog.userInfo) : errorLog.userInfo != null) return false;
        return !(agentDetail != null ? !agentDetail.equals(errorLog.agentDetail) : errorLog.agentDetail != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (loggerName != null ? loggerName.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (trace != null ? trace.hashCode() : 0);
        result = 31 * result + (errorDatetime != null ? errorDatetime.hashCode() : 0);
        result = 31 * result + (alertYn != null ? alertYn.hashCode() : 0);
        result = 31 * result + (headerMap != null ? headerMap.hashCode() : 0);
        result = 31 * result + (parameterMap != null ? parameterMap.hashCode() : 0);
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        result = 31 * result + (agentDetail != null ? agentDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorLog{" +
                "id=" + id +
                ", phase='" + phase + '\'' +
                ", system='" + system + '\'' +
                ", loggerName='" + loggerName + '\'' +
                ", serverName='" + serverName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", path='" + path + '\'' +
                ", message='" + message + '\'' +
                ", trace='" + trace + '\'' +
                ", errorDatetime=" + errorDatetime +
                ", alertYn='" + alertYn + '\'' +
                ", headerMap='" + headerMap + '\'' +
                ", parameterMap='" + parameterMap + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", agentDetail='" + agentDetail + '\'' +
                '}';
    }
}