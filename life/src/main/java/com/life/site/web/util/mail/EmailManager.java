package com.life.site.web.util.mail;

import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.life.site.config.param.CommonResult;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Component
public class EmailManager {

    private final Logger logger = LoggerFactory.getLogger(EmailManager.class);

   
    @Autowired
    private JavaMailSenderImpl mailSender;

    private Email from_email;
    private ArrayList<Email> to_email;
    private ArrayList<Email> bcc_email;
    private ArrayList<Email> cc_email;
    
    private String title;
    private String content;
    

    public void init(String from_address,String from_name,String title,String content) {
        this.from_email = new Email(from_address, from_name);
        this.to_email = new ArrayList<Email>();
        this.bcc_email = new ArrayList<Email>();
        this.cc_email = new ArrayList<Email>();
        this.title = title;
        this.content = content;
    }
    
    public void addTo(Email email) {
        to_email.add(email);
    }

    public void addTo(String address, String name) {
        Email email = new Email(address, name);
        to_email.add(email);
    }

    public void addBcc(Email email) {
        bcc_email.add(email);
    }

    public void addBcc(String address, String name) {
        Email email = new Email(address, name);
        bcc_email.add(email);
    }

    public void addCC(Email email) {
        cc_email.add(email);
    }

    public void addCC(String address, String name) {
        Email email = new Email(address, name);
        cc_email.add(email);
    }
    public CommonResult send(){
        CommonResult result = new CommonResult();
        
        if(to_email.size()==0) {
            result.setStatus(false);
            result.setMessage("이메일 수신자를 선택해주세요.");
            return result;
        }
        
        String profile = System.getProperty("spring.profiles.active");
        String charSet = "euc-kr";
        if(!profile.equals("prod")) {
            this.title = "[테스트] " + this.title;
        }

        try {
            final MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override public void prepare(MimeMessage mimeMessage) throws Exception {
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, charSet);

                    helper.setFrom(from_email.getAddress(), from_email.getName());
                    helper.setSubject(title);
                    helper.setText(content.toString().replaceAll("(\r\n|\n\r|\r|\n)", "<br>"), true);
                    
                    for(int i = 0 ; i< to_email.size();i++) {
                        if (!to_email.get(i).getAddress().isEmpty() && !to_email.get(i).getName().isEmpty()) 
                            helper.addTo(to_email.get(i).getAddress(), to_email.get(i).getName());
                    }
                    for(int i = 0 ; i< cc_email.size();i++) {
                        if (!cc_email.get(i).getAddress().isEmpty() && !cc_email.get(i).getName().isEmpty())
                            helper.addCc(cc_email.get(i).getAddress(), cc_email.get(i).getName());
                    }
                    
                    for(int i = 0 ; i< bcc_email.size();i++) {
                        if (!bcc_email.get(i).getAddress().isEmpty() && !bcc_email.get(i).getName().isEmpty())
                            helper.addBcc(bcc_email.get(i).getAddress(), bcc_email.get(i).getName());
                    }
                }
            };
            
            logger.info("================================ 메일 전송 시작 ================================");
            logger.info("================================ 메일 라인 출력 ================================");
            //logger.info(param.toString());
            
            mailSender.send(preparator);
            
            logger.info("================================= 메일 전송 끝 ================================");
            
            result.setStatus(true);
            result.setMessage("메일 전송이 완료되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("메일 전송을 실패했습니다.");
        }

        return result;
    }
}
