package com.akademik.krs.controller;

import com.akademik.krs.dto.KrsSubmitDto;
import com.akademik.krs.dto.StudentRecordsDto;
import com.akademik.krs.services.StudentRecordsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class StudentRecordsController {
    @Autowired
    private StudentRecordsService srService;

    @SneakyThrows(Exception.class)
    @PostMapping(path = "/student-record", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewRecord(@RequestBody StudentRecordsDto recordRequest){
        log.info("api POST /api/v1/student-record is hit.");
        return srService.addRecord(recordRequest);
    }

    @SneakyThrows(Exception.class)
    @PostMapping(path = "/student-records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitKRS(@RequestBody KrsSubmitDto krsRequest){
        log.info("api POST /api/v1/student-records is hit.");
        return srService.submitKRS(krsRequest);
    }

    @SneakyThrows(Exception.class)
    @GetMapping(path = "/student-record/{studentId}/{semester}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStudentRecordsPerSemester(@RequestParam Integer studentId, @RequestParam Integer semester){
        log.info("api GET /api/v1/student-record?studentId={studentId}&semester={semester}");
        return srService.getStudentRecords(studentId, semester);
    }

    @SneakyThrows(Exception.class)
    @DeleteMapping(path = "/student-record/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteStudentRecord(@PathVariable Integer id){
        log.info("api DELETE /api/v1/student-record/{id} is hit.");
        return srService.deleteStudentRecord(id);
    }

    @SneakyThrows(Exception.class)
    @DeleteMapping(path = "/student-records/bulk-delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteStudentRecordPerSemester(@RequestParam Integer studentId, @RequestParam Integer semester){
        log.info("api DELETE /api/v1/student-records/bulk-delete?studentId={studentId}&semester={semester}");
        return srService.deleteStudentRecordBySemester(studentId, semester);
    }
}
