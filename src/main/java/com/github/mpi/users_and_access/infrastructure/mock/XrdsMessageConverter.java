package com.github.mpi.users_and_access.infrastructure.mock;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class XrdsMessageConverter extends AbstractHttpMessageConverter<String>{

    public XrdsMessageConverter() {
        super(new MediaType("application", "xrds+xml", Charset.forName("UTF-8")));
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(String t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(t.getBytes("UTF-8"));
    }
}
