package com.ezbytes.SpringbootSecuritydemo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
      public class ExtractJwt {

        @Autowired
        public ObjectMapper mapper;


        public String payloadJWTExtraction(String token) throws JsonProcessingException {
            String[] chunks = token.replace("Bearer", "").split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            User user = mapper.readValue(payload,User.class);
            return user.getUserEmail();
        }


    }

    @Data
    class User{
        String userEmail;
    }

