package com.akademik.krs.controller;

import com.akademik.krs.dto.CoursesDto;
import com.akademik.krs.services.CoursesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class CoursesController {
    @Autowired
    private CoursesService coursesService;

    @SneakyThrows(Exception.class)
    @PostMapping(path = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewCourse(@RequestBody CoursesDto courseRequest){
        log.info("api POST /api/v1/course is hit.");
        return coursesService.addCourse(courseRequest);
    }

    @SneakyThrows(Exception.class)
    @GetMapping(path = "/course/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCourseById(@PathVariable Integer id){
        log.info("api GET /api/v1/course/{id} is hit.");
        return coursesService.getCourseById(id);
    }

    @SneakyThrows(Exception.class)
    @GetMapping(path = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCourseByQueryParam(@RequestParam(required = false) String name, @RequestParam(required = false) Integer credits){
        log.info("api GET /api/v1/course?name={name}&credits={credits} is hit.");
        return coursesService.getCourseByNameOrCredits(name, credits);
    }
}
