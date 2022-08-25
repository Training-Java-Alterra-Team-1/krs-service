package com.akademik.krs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KrsSubmitDto {
    private Integer studentId;
    private Integer semester;
}
