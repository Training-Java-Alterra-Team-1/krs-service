package com.akademik.krs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDto {
    private String courseName;
    private Integer courseCredits;
}
