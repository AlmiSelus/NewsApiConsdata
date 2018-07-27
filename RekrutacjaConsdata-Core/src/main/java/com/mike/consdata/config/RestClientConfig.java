package com.mike.consdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        List<HttpMessageConverter<?>> c = restTemplate.getMessageConverters();
        for(HttpMessageConverter<?> mc :c){
            if (mc instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter mcc = (StringHttpMessageConverter) mc;
                mcc.setWriteAcceptCharset(false);
            }
        }

        return restTemplate;

    }
}
