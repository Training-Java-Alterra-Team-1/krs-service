package com.akademik.krs.services;

import com.akademik.krs.dto.StudentRecordsDto;
import com.akademik.krs.models.Courses;
import com.akademik.krs.models.ScoreParameters;
import com.akademik.krs.models.StudentRecords;
import com.akademik.krs.models.Students;
import com.akademik.krs.repositories.StudentRecordsRepository;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackOn = Exception.class)
public class StudentRecordsService {
    @Autowired
    private StudentRecordsRepository srRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new record")
    public ResponseEntity<Object> addRecord(StudentRecordsDto recordReq){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(
                recordReq.getStudentId() == null || recordReq.getStudentId() == 0 ||
                        recordReq.getSemester() == null || recordReq.getSemester() == 0 ||
                        recordReq.getCourseId() == null || recordReq.getCourseId() == 0 ||
                        recordReq.getScoreId() == null || recordReq.getScoreId() == 0
        ){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "All fields are mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        StudentRecords newRecord = new StudentRecords();
        Students std = new Students();
        std.setId(recordReq.getStudentId());
        newRecord.setStudents(std);
        Courses crs = new Courses();
        crs.setId(recordReq.getCourseId());
        newRecord.setCourses(crs);
        newRecord.setSemester(recordReq.getSemester());
        ScoreParameters scr = new ScoreParameters();
        scr.setId(recordReq.getScoreId());
        newRecord.setScoreParameters(scr);

        srRepository.save(newRecord);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(recordReq);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all records")
    public ResponseEntity<Object> getAllRecords(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<StudentRecords> resp = srRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get student records")
    public ResponseEntity<Object> getStudentRecords(Integer studentId, Integer semester){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(studentId == null || studentId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Student Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }
        if(semester == null || semester == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Semester is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        List<StudentRecords> resp = new ArrayList<StudentRecords>();
        if(srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester) != null){
            resp = srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete student record")
    public ResponseEntity<Object> deleteStudentRecord(Integer studentRecordId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(studentRecordId == null || studentRecordId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Student record Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        StudentRecords sr = srRepository.findStudentRecordsById(studentRecordId);
        if(sr == null){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "No student record found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        srRepository.delete(sr);
        Map<String, Object> successResp = new HashMap<String, Object>();
        successResp.put("message", "Student record deleted.");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(successResp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete student record by semester")
    public ResponseEntity<Object> deleteStudentRecordBySemester(Integer studentId, Integer semester){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(studentId == null || studentId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Student Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }
        if(semester == null || semester == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Semester is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        List<StudentRecords> resp = new ArrayList<StudentRecords>();
        if(srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester) != null){
            resp = srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester);
            srRepository.deleteAll(resp);
        }else{
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "No student records found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        Map<String, Object> successResp = new HashMap<String, Object>();
        successResp.put("message", "Student records deleted.");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(successResp);
    }
}
