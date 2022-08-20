package com.akademik.krs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorsDto {
    private String majorName;
    private Integer departmentId;
    private Integer degreeId;
}
