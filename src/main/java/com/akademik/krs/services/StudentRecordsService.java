package com.akademik.krs.services;

import com.akademik.krs.dto.KrsSubmitDto;
import com.akademik.krs.dto.StudentRecordsDto;
import com.akademik.krs.models.*;
import com.akademik.krs.repositories.KrsParametersRepository;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackOn = Exception.class)
public class StudentRecordsService {
    @Autowired
    private StudentRecordsRepository srRepository;

    @Autowired
    private KrsParametersRepository kpRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new record")
    public ResponseEntity<Object> addRecord(StudentRecordsDto recordReq){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<String, Object>();
        if(recordReq.getStudentId() == null || recordReq.getStudentId() == 0
                || recordReq.getSemester() == null || recordReq.getSemester() == 0
                || recordReq.getCourseId() == null || recordReq.getCourseId() == 0
                || recordReq.getScoreId() == null || recordReq.getScoreId() == 0)
        {
            response.put("success", false);
            response.put("error", "All fields are mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        StudentRecords studentRecord = new StudentRecords();
        Students std = new Students();
        std.setId(recordReq.getStudentId());
        studentRecord.setStudents(std);
        Courses crs = new Courses();
        crs.setId(recordReq.getCourseId());
        studentRecord.setCourses(crs);
        studentRecord.setSemester(recordReq.getSemester());
        ScoreParameters scr = new ScoreParameters();
        scr.setId(recordReq.getScoreId());
        studentRecord.setScoreParameters(scr);
        LocalDateTime todayDateTime = LocalDateTime.now();
        studentRecord.setCreatedAt(todayDateTime);

        srRepository.save(studentRecord);

        response.put("success", true);
        response.put("data", studentRecord);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Submit KRS per Semester")
    public ResponseEntity<Object> submitKRS(KrsSubmitDto krsDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<String, Object>();
        if(krsDto.getStudentId() == null || krsDto.getStudentId() == 0){
            response.put("success", false);
            response.put("error", "Student Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(krsDto.getSemester() == null || krsDto.getSemester() == 0){
            response.put("success", false);
            response.put("error", "Semester is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        List<StudentRecords> listRec = new ArrayList<StudentRecords>();
        if(srRepository.findStudentRecordsByStudentsIdAndSemester(krsDto.getStudentId(), krsDto.getSemester()) != null){
            listRec = srRepository.findStudentRecordsByStudentsIdAndSemester(krsDto.getStudentId(), krsDto.getSemester());
        }else{
            response.put("success", false);
            response.put("error", "The student did not take any courses on this semester.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        KrsParameters kparam = kpRepository.findKrsParametersByParameterCodeAndValue("KRS_STATUS", "SUBMITTED");

        for(StudentRecords item: listRec){
            KrsParameters krs = new KrsParameters();
            krs.setId(kparam.getId());
            item.setKrsParameters(krs);
            LocalDateTime todayDateTime = LocalDateTime.now();
            item.setUpdatedAt(todayDateTime);
            srRepository.save(item);
        }

        response.put("success", true);
        response.put("message", listRec.size() + " student records processed. KRS submitted.");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
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

        Map<String, Object> response = new HashMap<String, Object>();
        if(studentId == null || studentId == 0){
            response.put("success", false);
            response.put("error", "Student Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(semester == null || semester == 0){
            response.put("success", false);
            response.put("error", "Semester is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        List<StudentRecords> studentRecords = new ArrayList<StudentRecords>();
        if(srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester) != null){
            studentRecords = srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester);
        }

        response.put("success", true);
        response.put("data", studentRecords);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete student record")
    public ResponseEntity<Object> deleteStudentRecord(Integer studentRecordId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<String, Object>();
        if(studentRecordId == null || studentRecordId == 0){
            response.put("success", false);
            response.put("error", "Student record Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        StudentRecords sr = srRepository.findStudentRecordsById(studentRecordId);
        if(sr == null){
            response.put("success", false);
            response.put("error", "No student record found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        srRepository.delete(sr);
        response.put("success", true);
        response.put("message", "Selected student record has been deleted");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete student record by semester")
    public ResponseEntity<Object> deleteStudentRecordBySemester(Integer studentId, Integer semester){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<String, Object>();
        if(studentId == null || studentId == 0){
            response.put("success", false);
            response.put("error", "Student Id is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(semester == null || semester == 0){
            response.put("success", false);
            response.put("error", "Semester is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        List<StudentRecords> studentRecords = new ArrayList<StudentRecords>();
        if(srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester) != null){
            studentRecords = srRepository.findStudentRecordsByStudentsIdAndSemester(studentId, semester);
            srRepository.deleteAll(studentRecords);
        }else{
            response.put("success", false);
            response.put("error", "No student records found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        response.put("success", true);
        response.put("message", "Selected student records has been deleted");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }
}
