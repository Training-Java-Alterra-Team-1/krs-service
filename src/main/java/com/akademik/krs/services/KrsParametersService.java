package com.akademik.krs.services;

import com.akademik.krs.models.KrsParameters;
import com.akademik.krs.repositories.KrsParametersRepository;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackOn = Exception.class)
public class KrsParametersService {
    @Autowired
    private KrsParametersRepository kpRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new KRS Parameter")
    public ResponseEntity<Object> addKrsParameter(String paramCode, String value){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(
                paramCode == null || paramCode == "" ||
                        value == null || value == ""
        ){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "All fields are mandatory.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        KrsParameters krs = new KrsParameters();
        krs.setParameterCode(paramCode);
        krs.setValue(value);
        LocalDateTime todayDateTime = LocalDateTime.now();
        krs.setCreatedAt(todayDateTime);

        kpRepository.save(krs);

        Map<String, Object> successResp = new HashMap<String, Object>();
        successResp.put("message", "KRS parameter " + paramCode + " has successfully added.");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(successResp);
    }
}
