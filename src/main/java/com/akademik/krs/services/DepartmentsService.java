package com.akademik.krs.services;

import com.akademik.krs.dto.DepartmentDto;
import com.akademik.krs.models.Departments;
import com.akademik.krs.repositories.DepartmentsRepository;
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
public class DepartmentsService {
    @Autowired
    private DepartmentsRepository departmentsRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new department")
    public ResponseEntity<Object> addDepartment(DepartmentDto departmentRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Departments theDepartment = new Departments();
        theDepartment.setName(departmentRequest.getDepartmentName());
        LocalDateTime todayDateTime = LocalDateTime.now();
        theDepartment.setCreatedAt(todayDateTime);

        departmentsRepository.save(theDepartment);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(theDepartment);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all departments")
    public ResponseEntity<Object> getAllDepartments(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Departments> resp = departmentsRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }
}
