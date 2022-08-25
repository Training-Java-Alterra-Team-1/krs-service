package com.akademik.krs.services;

import com.akademik.krs.dto.MajorsDto;
import com.akademik.krs.models.Degrees;
import com.akademik.krs.models.Departments;
import com.akademik.krs.models.Majors;
import com.akademik.krs.repositories.MajorsRepository;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class MajorsService {
    @Autowired
    private MajorsRepository majorsRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new major")
    public ResponseEntity<Object> addMajor(MajorsDto majorRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Majors theMajor = new Majors();
        theMajor.setName(majorRequest.getMajorName());
        Degrees theDegree = new Degrees();
        theDegree.setId(majorRequest.getDegreeId());
        theMajor.setDegrees(theDegree);
        Departments theDepartment = new Departments();
        theDepartment.setId(majorRequest.getDepartmentId());
        theMajor.setDepartments(theDepartment);
        LocalDateTime todayDateTime = LocalDateTime.now();
        theMajor.setCreatedAt(todayDateTime);

        majorsRepository.save(theMajor);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(theMajor);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all majors")
    public ResponseEntity<Object> getAllMajors(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Majors> resp = majorsRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get major by id")
    public ResponseEntity<Object> getMajorById(Integer majorId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Majors majors = majorsRepository.findMajorsById(majorId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(majors);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get major by name")
    public ResponseEntity<Object> getMajorByName(String majorName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Majors majors = majorsRepository.findMajorsByName(majorName);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(majors);
    }
}
