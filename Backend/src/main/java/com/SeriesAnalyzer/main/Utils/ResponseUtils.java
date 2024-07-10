package com.SeriesAnalyzer.main.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseUtils {

    private String message;

    public static ResponseEntity<String> CreateSuccessResponse(String message){
        return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
    }

    public static ResponseEntity<String> createErrorResponse(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body("{\"error\": \"" + message + "\"}");
    }

}
