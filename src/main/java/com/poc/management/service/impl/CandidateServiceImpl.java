package com.poc.management.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.poc.management.dto.CandidateDto;
import com.poc.management.entity.Candidates;
import com.poc.management.entity.Gender;
import com.poc.management.entity.Positions;
import com.poc.management.exceptions.RestApiException;
import com.poc.management.repository.CandidateRepository;
import com.poc.management.repository.GenderRepository;
import com.poc.management.repository.PositionRepository;
import com.poc.management.service.CandidateService;
import com.poc.management.util.PagableObject;
import com.poc.management.util.ResponseUtil;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final GenderRepository genderRepository;
    private final PositionRepository positionRepository;
    private final CandidateRepository candidateRepository;
    private PagableObject pagableObject;

    @Override
    public RestApiResponse<CandidateDto> addCandidate(CandidateDto candidateDto) {
        Candidates candidates=pagableObject.map(candidateDto,Candidates.class);
        if (candidateDto.getPositionId() != null && !candidateDto.getPositionId().isEmpty()) {
            List<Positions> positions = positionRepository.findAllById(candidateDto.getPositionId());
            candidates.setPositions(positions);
        }
        Candidates candidates1 = candidateRepository.saveAndFlush(candidates);
        CandidateDto candidatesDto=pagableObject.map(candidates1,CandidateDto.class);
        return ResponseUtil.getResponse(candidatesDto,"Candidate Created");
    }

    @Override
    public RestApiResponse<String> updateCandidate(Long id, String candidateDto) {
        CandidateDto request = pagableObject.readValue(candidateDto, CandidateDto.class);

        JsonNode jsonNode = pagableObject.getJsonNode(candidateDto);
        Candidates candidates = findBy(id);
        Map<String, Consumer<String>> stringFieldUpdaters=Map.of(
                "name",candidates::setName,
                "email",candidates::setEmail
        );

        Map<String, Consumer<LocalDate>> localDateFieldUpdaters=Map.of(
                "dob",candidates::setDob
        );

        Map<String, Consumer<List<Positions>>> positionFieldUpdaters=Map.of(
                "positionId",candidates::setPositions
        );

        Map<String, Consumer<Gender>> genderFieldUpdaters=Map.of(
                "genderId", candidates::setGender
        );

        updateStringFieldsIfPresentString(stringFieldUpdaters,jsonNode,request);
        updateLocalDateFieldsIfPresent(localDateFieldUpdaters,jsonNode,request);
        updatePositionFieldsIfPresent(positionFieldUpdaters,jsonNode,request);
        updateGenderFieldsIfPresent(genderFieldUpdaters, jsonNode, request);
        pagableObject.validate(candidates);
        candidateRepository.saveAndFlush(candidates);
        return ResponseUtil.getResponseMessage("Candidate Updated Successfully");
    }

    @Override
    public RestApiResponse<Page<CandidateDto>> getAllCandidate(Pageable pageable) {
        Page<Candidates> candidatesPage = candidateRepository.findAll(pageable);
        List<CandidateDto> candidateDtos= pagableObject.mapListToClass(candidatesPage.getContent(),CandidateDto.class);
        Page<CandidateDto> pagedCandidateDto= new PageImpl<>(candidateDtos,pageable, candidatesPage.getTotalPages());
        return ResponseUtil.getResponse(pagedCandidateDto,"Candidate");
    }

    @Override
    public RestApiResponse<String> deleteCandidate(Long id) {
        return ResponseUtil.getResponseMessage(
                candidateRepository.findById(id).map(candidate -> {
                    candidateRepository.delete(candidate);
                    return "Candidate deleted successfully";
                }).orElseThrow(() ->
                        new RestApiException(String.format("Candidate with id %s not found", id), HttpStatus.NOT_FOUND))
        );
    }



    private void updateGenderFieldsIfPresent(Map<String, Consumer<Gender>> genderFieldUpdaters,
                                             JsonNode jsonNode,
                                             CandidateDto request) {
        genderFieldUpdaters.forEach((fieldName, fieldSetter) -> {
            if (jsonNode.has(fieldName)) {
                Long genderId = getGenderFromRequest(fieldName, request);
                updateFieldIfGenderPresent(genderId, fieldSetter);
            }
        });
    }

    private void updateFieldIfGenderPresent(Long genderId, Consumer<Gender> fieldSetter) {
        if (genderId != null) {
            Gender gender = genderRepository.findById(genderId)
                    .orElseThrow(() -> new RestApiException(String.format("Gender with id: %s not found",genderId),HttpStatus.NOT_FOUND));
            fieldSetter.accept(gender);
        }
    }

    private Long getGenderFromRequest(String fieldName, CandidateDto request) {
        return switch (fieldName) {
            case "gender" -> request.getGenderId();
            default -> null;
        };
    }


    private void updatePositionFieldsIfPresent(Map<String, Consumer<List<Positions>>> positionFieldUpdaters,
                                               JsonNode jsonNode, CandidateDto request) {
        positionFieldUpdaters.forEach((fieldName, fieldSetter)->{
                if(jsonNode.has(fieldName)){
                    List<Long> fieldValue= getPositionListFromRequest(fieldName,request);
                    updateFieldIfPresentPosition(fieldValue,fieldSetter);
                }
                });
    }

    private void updateFieldIfPresentPosition(List<Long> fieldValue, Consumer<List<Positions>> fieldSetter) {
        if (fieldValue != null && !fieldValue.isEmpty()) {
            List<Positions> positionList;
            try{
                positionList = positionRepository.findAllById(fieldValue);
            } catch (RestApiException e) {
                 throw new RestApiException(String.format("Position with id: %s not found",fieldValue),HttpStatus.BAD_REQUEST);
            }
            fieldSetter.accept(positionList);
        }
    }

    private List<Long> getPositionListFromRequest(String fieldName, CandidateDto request) {
        return switch (fieldName){
            case "positionId"->request.getPositionId();
            default -> null;
        };
    }

    private void updateLocalDateFieldsIfPresent(Map<String, Consumer<LocalDate>> localDateFieldUpdaters, JsonNode jsonNode, CandidateDto request) {
        localDateFieldUpdaters.forEach( (fieldName,fieldSetter)->{
            if (jsonNode.has(fieldName)){
                LocalDate fieldValue= getLocalDateFromRequest(request,fieldName);
                updateFieldIfPresentLocalDate(fieldSetter,fieldValue);
            }
        });
    }
    private void updateStringFieldsIfPresentString(Map<String, Consumer<String>> stringFieldUpdaters, JsonNode jsonNode, CandidateDto request) {
        stringFieldUpdaters.forEach((fieldName, fieldSetter)->{
            if (jsonNode.has(fieldName)){
                String fieldValue=getStringFromRequest(request,fieldName);
                updateFieldIfPresent(fieldValue,fieldSetter);
            }
        });

    }

    private String getStringFromRequest(CandidateDto request, String fieldName) {
        return switch (fieldName){
            case "name"-> request.getName();
            case "email"-> request.getEmail();
            default -> null;
        };
    }
    private LocalDate getLocalDateFromRequest(CandidateDto request, String fieldName) {
        return switch (fieldName){
            case "dob"-> request.getDob();
            default -> null;
        };
    }

    private void updateFieldIfPresentLocalDate(Consumer<LocalDate> fieldSetter, LocalDate fieldValue) {
        fieldSetter.accept(fieldValue);
    }
    private void updateFieldIfPresent(String fieldValue, Consumer<String> fieldSetter) {
        if (StringUtils.hasText(fieldValue)) {
            fieldSetter.accept(fieldValue);
        }
    }

    private Candidates findBy(Long id){
        return candidateRepository.findById(id)
                .orElseThrow(()-> new RestApiException(String.format("Candidate with id: %s not found",id),HttpStatus.NOT_FOUND));
    }

}
