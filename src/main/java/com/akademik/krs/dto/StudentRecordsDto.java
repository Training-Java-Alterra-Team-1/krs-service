package com.akademik.krs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRecordsDto {
    private Integer studentId;
    private Integer courseId;
    private Integer semester;
    private Integer scoreId;
}
