package com.life.site.web.util.mail;

import lombok.Value;

@Value
public class Email {
	
	private String address;
	private String name;
	
}

//FROM ADDRESS, FROM NAME은 필수
