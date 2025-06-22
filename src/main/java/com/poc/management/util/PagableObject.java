package com.poc.management.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PagableObject {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostConstruct
    public void init() {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }


    public <S,T> T map (S source, Class<T> target){
        return modelMapper.map(source,target);
    }


    public <S, T> void map(S source, T target) {
        modelMapper.map(source, target);
    }


    public <S, T> List<T> mapListToClass(List<S> source, Class<T> targetClass){
        return source.stream().map(
                s->
                        modelMapper.map(s, targetClass)
        ).collect(Collectors.toList());
    }

    public <T> void validate(T object){
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (violations.isEmpty()) {
            return;
        }
        throw new ConstraintViolationException(violations);
    }

    public <T> T readValue(String content, Class<T> targetClass) {
        try {
            return objectMapper.readValue(content, targetClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public JsonNode getJsonNode(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            return null;
        }
    }

}
