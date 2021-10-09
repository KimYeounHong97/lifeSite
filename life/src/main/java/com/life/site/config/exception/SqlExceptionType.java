package com.life.site.config.exception;


import java.util.Arrays;
public enum SqlExceptionType
{
    SIZE_ZERO("SIZE_ZERO","저장할 항목이 0개입니다."),
    ORA_00001("ORA-00001","데이터가 중복등록 되었습니다."),
    ORA_01400("ORA-01400","필수항목의 값이 누락되었습니다."),
    ORA_01407("ORA-01407","필수항목의 값이 누락되었습니다."),
    ORA_01401("ORA-01401","입력하는 값이 필드의 최대크기보다 큽니다."),
    ORA_01438("ORA-01438","입력하는 숫자값이 필드의 최대크기보다 큽니다."),
    ORA_01722("ORA-01722","숫자항목에 문자가 입력되었습니다."),
    ORA_12899("ORA-12899","입력하는 값이 너무큽니다."),
    ORA_02292("ORA-02292","해당 데이터를 사용하는 자식 레코드가 있습니다."),
    ORA_ETC("ORA-","Database 오류입니다.");


    private String code;
    private String msg;

    SqlExceptionType(String code,String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }
    public String getCode() {
        return code;
    }
    //"ORA-01400"등을 읽어서 해당 ENUM을 꺼내줌
    public static SqlExceptionType getFromCode(String code) {
        return Arrays.asList(values()).stream().filter(t -> t.getCode().equals(code)).findAny().orElse(ORA_ETC);
    }
}
 
