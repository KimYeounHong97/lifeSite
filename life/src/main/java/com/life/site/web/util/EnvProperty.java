package com.life.site.web.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Getter;

@Configuration
@Getter
@Primary
public class EnvProperty {
    
    @Value("${env.attfile-path}")
    private String ATTFILE_PATH;
}
