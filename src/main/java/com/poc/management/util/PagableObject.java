package com.poc.management.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PagableObject {
    private ModelMapper modelMapper;
    public <S,T> T map (S source, Class<T> target){
        return modelMapper.map(source,target);
    }

    public <S, T> List<T> mapListToClass(List<S> source, Class<T> targetClass){
        return source.stream().map(
                s->
                        modelMapper.map(s, targetClass)
        ).collect(Collectors.toList());
    }

}
