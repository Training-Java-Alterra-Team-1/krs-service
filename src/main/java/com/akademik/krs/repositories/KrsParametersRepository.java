package com.akademik.krs.repositories;

import com.akademik.krs.models.KrsParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KrsParametersRepository extends JpaRepository<KrsParameters, Long> {
    KrsParameters findKrsParametersById(Integer krsId);
    KrsParameters findKrsParametersByParameterCodeAndValue(String krsParameterCode, String krsParameterValue);
}
