package com.akademik.krs.repositories;

import com.akademik.krs.models.Degrees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreesRepository extends JpaRepository<Degrees, Long> {
    Degrees findDegreesById(Integer degreeId);
    Degrees findDegreesByName(String degreeName);
}
