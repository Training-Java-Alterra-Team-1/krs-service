package com.akademik.krs.repositories;

import com.akademik.krs.models.ScoreParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreParametersRepository extends JpaRepository<ScoreParameters, Long> {
    ScoreParameters findScoreParametersById(Integer scoreId);
    ScoreParameters findScoreParametersByScoreGrade(String scoreGrade);
}
