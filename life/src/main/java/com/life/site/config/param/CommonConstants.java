package com.life.site.config.param;

public class CommonConstants {
    public static String UPLOAD_PATH = "";
    public static String BLOCK_KEYWORD = "<script;</script>;<iframe";

    public static final String LIFE_SESSION = "life";
    public static final String COOKIE_REMEMBER_ID = "LOGIN_COOKIE";
    public static final String COOKIE_LOCALE = "cookie_language";
    public static final String COOKIE_TYPE = "cookie_type";

    public static final String STR_MOBILE = "Mobile";
    public static final String STR_PC = "PC";
    public static final String STR_NO_INFO = "No Info";
    public static final String STR_SYSTEM = "system";

    public static final String STR_SUCCESS = "S";
    public static final String STR_ERROR = "E";
    public static final String STR_FAIL = "F";
    public static final String STR_Y = "Y";
    public static final String STR_N = "N";

    public static final String API_GET_COUNTRY = "http://ip2c.org/";
    public static final String LOCALE_KO = "ko";

    public static final String MAIN_PAGE = "/";

    public final class Params {
        public static final String MESSAGE = "message";
        public static final String LOGIN_USERID = "loginUserId";
        public static final String USER = "user";
        public static final String ORG = "org";
        public static final String LOGIN_ID = "LOGIN_ID";
        public static final String USER_ID = "USER_ID";
        public static final String USER_NM = "USER_NM";
        public static final String COMP_CD = "COMP_CD";
        public static final String ORG_CD = "ORG_CD";
        public static final String PROGRAM_CD = "PROGRAM_CD";
        public static final String AUTH_CD = "AUTH_CD";
        public static final String BEFORE_AUTH_CD = "BEFORE_AUTH_CD";
        public static final String COPY_AUTH_CD = "COPY_AUTH_CD";
        public static final String MENU_CD = "MENU_CD";
        public static final String MENU_NM = "MENU_NM";
        public static final String EMAIL = "EMAIL";
        public static final String USE_FL = "USE_FL";
        public static final String OPEN_ALL_FL = "OPEN_ALL_FL";
        public static final String CERT_TOKEN = "CERT_TOKEN";
        public static final String SEND_TO = "SEND_TO";
        public static final String LANG = "LANG";
        public static final String PASSWD = "PASSWD";
        public static final String REMEMBER_ID = "REMEMBER_ID";
        public static final String OLD_PW = "OLD_PW";
        public static final String JOB_TARGET = "JOB_TARGET";
        public static final String JOB_GUBUN = "JOB_GUBUN";
        public static final String ACT_ID = "ACT_ID";
        public static final String ACT_DT = "ACT_DT";
        public static final String TARGET_ID = "TARGET_ID";
        public static final String POPUP_CD = "POPUP_CD";
        public static final String POPUP_TITLE = "POPUP_TITLE";
        public static final String POPUP_CONTENTS_ONLY = "POPUP_CONTENTS_ONLY";
        public static final String POPUP_CONTENTS = "POPUP_CONTENTS";
        public static final String POPUP_X = "POPUP_X";
        public static final String POPUP_Y = "POPUP_Y";
        public static final String FILE_UID = "FILE_UID";
        public static final String START_DT = "START_DT";
        public static final String CLOSE_DT = "CLOSE_DT";
        public static final String START_TM = "START_TM";
        public static final String CLOSE_TM = "CLOSE_TM";
        public static final String TODAY_SHOW_FL = "TODAY_SHOW_FL";
        public static final String POPUP_TARGET = "POPUP_TARGET";
        public static final String POPUP_TARGET_LEN = "POPUP_TARGET_LEN";
        public static final String CONTENTS = "CONTENTS";
        public static final String FILE_GRP_ID = "FILE_GRP_ID";
        public static final String BOARD_CD = "BOARD_CD";
        public static final String WRITING_CD = "WRITING_CD";
        public static final String CALLED_PROGRAM = "CALLED_PROGRAM";
        public static final String PROGRAM_URL = "PROGRAM_URL";
        public static final String IF_NAME = "IF_NAME";
        public static final String IF_ID = "IF_ID";
        public static final String PROGRAM_KEY = "PROGRAM_KEY";
        public static final String APRV_EVENT = "APRV_EVENT ";
        public static final String RESULT_CODE = "RESULT_CODE";
        public static final String RESULT_MESSAGE = "RESULT_MESSAGE ";
        public static final String AUTH_E = "AUTH_E";
        public static final String AUTH_D = "AUTH_D";
        public static final String AUTH_P = "AUTH_P";
        public static final String PROGRAM_MANUAL = "PROGRAM_MANUAL";
        public static final String BIGO = "BIGO";
    }

    public final class GridParams {
        public static final String C = "C";
        public static final String U = "U";
        public static final String D = "D";
    }

    public final class AccessGubun {
        public static final String LOGIN = "LOGIN";
        public static final String SSO = "SSO";
        public static final String PAGE = "PAGE";
        public static final String CHGORG = "CHGORG"; // 사업장 변경하여 재로그인 (로그인 상태에서 실행)
    }

    public final class Logs {
        public static final String USER_ID = "USER_ID";
        public static final String USER_GRADE_CD = "USER_GRADE_CD";
        public static final String USER_GRADE_NM = "USER_GRADE_NM";
        public static final String ACC_URL = "ACC_URL";
        public static final String ACC_PARAMS = "ACC_PARAMS";
        public static final String ACC_IP = "ACC_IP";
        public static final String ACC_TYPE = "ACC_TYPE";
        public static final String AGENT = "AGENT";
        public static final String SUCCESS_FL = "SUCCESS_FL";
    }

    public final class Email {
        public static final String FROM_ADDR = "no-reply@hansol.com";
        public static final String FROM_NAME = "웹표준 시스템";
    }
    
    public final class History {
        public static final String USER = "사용자";
        public static final String AUTH = "권한";
        public static final String AUTH_USER = "권한_사용자";
        public static final String AUTH_ORG_USER = "권한_사업장_사용자";
        public static final String AUTH_PROGRAM = "권한_프로그램";
        public static final String INSERT = "신규";
        public static final String UPDATE = "수정";
        public static final String DELETE = "삭제";
    }
}
