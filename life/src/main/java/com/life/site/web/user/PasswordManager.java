package com.life.site.web.user;

import java.security.MessageDigest;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordManager {
    private int pwdLength = 8;
    private final char[] wordTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
                                       'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                       'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
                                       'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
                                       '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    /**
     * MD5 암호화
     * 
     * @param strVal
     * @return
     * @throws Exception
     */
    public String getMD5(String strVal) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();

        byte[] digest = MessageDigest.getInstance("MD5").digest(strVal.getBytes());
        stringBuffer.setLength(0);

        for (int i = 0; i < digest.length; i++) {
            stringBuffer.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
            stringBuffer.append(Integer.toString(digest[i] & 0x0f, 16));
        }
        return stringBuffer.toString();
    }

    /**
     * SHA256 암호화
     * 
     * @param strVal
     * @return
     * @throws Exception
     */
    public String getSHA256(String strVal) throws Exception {
        String SHA = "";
        MessageDigest sh = MessageDigest.getInstance("SHA-256");
        sh.update(strVal.getBytes());
        byte byteData[] = sh.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        SHA = sb.toString();
        return SHA;
    }

    /**
     * 임시 비밀번호 생성
     * 
     * @return
     */
    public String getRandomString() {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        int length = 10;

        String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0".split(",");

        for (int i = 0; i < length; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }
    
    public String getRandomPassword() {
        Random random = new Random(System.currentTimeMillis());
        int tableLength = wordTable.length;
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < pwdLength; i++) {
            buf.append(wordTable[random.nextInt(tableLength)]);
        }
        
        return buf.toString();
    }
}
