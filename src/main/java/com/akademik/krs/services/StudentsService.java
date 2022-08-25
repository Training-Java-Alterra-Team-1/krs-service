package com.akademik.krs.services;

import com.akademik.krs.dto.StudentsDto;
import com.akademik.krs.models.Majors;
import com.akademik.krs.models.Students;
import com.akademik.krs.repositories.MajorsRepository;
import com.akademik.krs.repositories.StudentsRepository;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class StudentsService {
    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private MajorsRepository majorsRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new student")
    public ResponseEntity<Object> addStudent(StudentsDto studentReq){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Students students = new Students();
        students.setAddress(studentReq.getStudentAddress());
        students.setDob(studentReq.getStudentDob());
        students.setName(studentReq.getStudentName());
        students.setGender(studentReq.getStudentGender());

        Majors theMajor = new Majors();
        theMajor = majorsRepository.findMajorsById(studentReq.getMajorId());

        students.setMajors(theMajor);

        studentsRepository.save(students);

        return  ResponseEntity.status(HttpStatus.OK).headers(headers).body(studentReq);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all students")
    public ResponseEntity<Object> getStudents(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Students> resp = studentsRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get student by Id")
    public ResponseEntity<Object> getStudentById(Integer studentId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Students students = studentsRepository.findStudentsById(studentId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(students);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get student by name")
    public ResponseEntity<Object> getStudentByName(String studentName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Students students = studentsRepository.findStudentsByName(studentName);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(students);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Update data student")
    public ResponseEntity<Object> updateStudentById(Integer studentId, StudentsDto studentReq){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Students students = studentsRepository.findStudentsById(studentId);
        if(studentReq.getStudentDob() != null)
            students.setDob(studentReq.getStudentDob());
        if(studentReq.getStudentAddress() != null && studentReq.getStudentAddress() != "")
            students.setAddress(studentReq.getStudentAddress());
        if(studentReq.getStudentName() != null && studentReq.getStudentName() != "")
            students.setName(studentReq.getStudentName());
        if(studentReq.getStudentGender() != null && studentReq.getStudentGender() != "")
            students.setGender(studentReq.getStudentGender());
        if(studentReq.getMajorId() != null){
            Majors major = new Majors();
            major.setId(studentReq.getMajorId());
            students.setMajors(major);
        }

        studentsRepository.save(students);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(studentReq);
    }
}
