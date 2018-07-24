package com.mike.consdata.news;

import com.mike.consdata.news.io.NewsRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewsRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewsRequest newsRequest = (NewsRequest) target;
        if(newsRequest.getCategory() == null) {
            errors.reject("Category is null");
            return;
        }

        if(newsRequest.getCountry() == null) {
            errors.reject("Country is null");
            return;
        }

        if(newsRequest.getCountry().isEmpty()) {
            errors.reject("Country is empty");
            return;
        }

        if(newsRequest.getCategory().isEmpty()) {
            errors.reject("Category is empty");
            return;
        }

    }

}
