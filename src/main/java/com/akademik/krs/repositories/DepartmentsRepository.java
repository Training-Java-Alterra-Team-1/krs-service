package com.akademik.krs.repositories;

import com.akademik.krs.models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    Departments findDepartmentsById(Integer departmentId);
    Departments findDepartmentsByName(String departmentName);
}
