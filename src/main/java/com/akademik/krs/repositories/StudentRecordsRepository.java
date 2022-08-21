package com.akademik.krs.repositories;

import com.akademik.krs.models.StudentRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRecordsRepository extends JpaRepository<StudentRecords, Long> {
    StudentRecords findStudentRecordsById(Integer recordId);
    List<StudentRecords> findStudentRecordsByStudentsIdAndSemester(Integer studentId, Integer semester);
}
