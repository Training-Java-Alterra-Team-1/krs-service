package com.akademik.krs.services;

import com.akademik.krs.dto.CoursesDto;
import com.akademik.krs.models.Courses;
import com.akademik.krs.repositories.CoursesRepository;
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
public class CoursesService {
    @Autowired
    private CoursesRepository coursesRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new course")
    public ResponseEntity<Object> addCourse(CoursesDto courseRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(courseRequest.getCourseCredits() == 0) {
            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("error_message", "Credits cannot equal to zero.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(resp);
        }
        if(courseRequest.getCourseName() == "" || courseRequest.getCourseName() == null){
            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("error_message", "Course name cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(resp);
        }

        Courses theCourse = new Courses();
        theCourse.setName(courseRequest.getCourseName());
        theCourse.setCredits(courseRequest.getCourseCredits());
        LocalDateTime todayDateTime = LocalDateTime.now();
        theCourse.setCreatedAt(todayDateTime);

        coursesRepository.save(theCourse);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(theCourse);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all Courses")
    public ResponseEntity<Object> getAllCourses(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Courses> respon = coursesRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(respon);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get course by Id")
    public ResponseEntity<Object> getCourseById(Integer courseId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Courses courses = coursesRepository.findCoursesById(courseId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(courses);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get course by Name or Credits")
    public ResponseEntity<Object> getCourseByNameOrCredits(String courseName, Integer courseCredits){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Courses> lcourses = new ArrayList<Courses>();

        if(courseName != null && courseCredits != null)
            lcourses = coursesRepository.findCoursesByNameOrCredits(courseName, courseCredits);
        if(courseName == null && courseCredits != null)
            lcourses = coursesRepository.findCoursesByCredits(courseCredits);
        if(courseName != null && courseCredits == null) {
            if(coursesRepository.findCoursesByName(courseName) != null) {
                lcourses.add(coursesRepository.findCoursesByName(courseName));
            }
        }
        if(courseName == null && courseCredits == null)
            lcourses = coursesRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lcourses);
    }
}
