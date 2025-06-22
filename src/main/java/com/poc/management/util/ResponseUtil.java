package com.poc.management.util;

import java.time.LocalDate;

public class ResponseUtil {

    public static <T> RestApiResponse<T> getResponse(T data, String message){
        return RestApiResponse.<T>builder()
                .data(data)
                .timestamp(LocalDate.now())
                .build();
    }

    public static <T> RestApiResponse<T> getResponseMessage(Object message){
        return RestApiResponse.<T>builder()
                .message(message)
                .timestamp(LocalDate.now())
                .build();
    }


}
