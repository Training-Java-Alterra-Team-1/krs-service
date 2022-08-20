package com.akademik.krs.repositories;

import com.akademik.krs.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    Courses findCoursesById(Integer courseId);
    Courses findCoursesByName(String courseName);
    List<Courses> findCoursesByCredits(Integer courseCredits);
    List<Courses> findCoursesByNameOrCredits(String courseName, Integer courseCredits);
}
