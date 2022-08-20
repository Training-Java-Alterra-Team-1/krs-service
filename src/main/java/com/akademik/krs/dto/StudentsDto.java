package com.akademik.krs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsDto {
    private String studentName;
    private Date studentDob;
    private String studentGender; // "M" or "F"
    private String studentAddress;
    private Integer majorId;
}
